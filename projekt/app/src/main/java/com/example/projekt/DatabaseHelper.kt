package com.example.projekt

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "articles.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_ARTICLES = "articles"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DATE = "date"

        private const val TABLE_ELEMENTS = "elements"
        private const val COLUMN_ARTICLE_ID = "article_id"
        private const val COLUMN_TYPE = "type"
        private const val COLUMN_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createArticlesTable = """
            CREATE TABLE $TABLE_ARTICLES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT,
                $COLUMN_DATE TEXT
            )
        """.trimIndent()
        db.execSQL(createArticlesTable)

        val createElementsTable = """
            CREATE TABLE $TABLE_ELEMENTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_ARTICLE_ID INTEGER,
                $COLUMN_TYPE TEXT,
                $COLUMN_CONTENT TEXT,
                FOREIGN KEY($COLUMN_ARTICLE_ID) REFERENCES $TABLE_ARTICLES($COLUMN_ID)
            )
        """.trimIndent()
        db.execSQL(createElementsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ELEMENTS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ARTICLES")
        onCreate(db)
    }

    fun insertArticle(article: Article): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, article.title)
            put(COLUMN_DATE, article.date)
        }
        val articleId = db.insert(TABLE_ARTICLES, null, values)

        article.elements.forEach { element ->
            val elementValues = ContentValues().apply {
                put(COLUMN_ARTICLE_ID, articleId)
                put(COLUMN_TYPE, when (element) {
                    is ArticleElement.Text -> "text"
                    is ArticleElement.Image -> "image"
                })
                put(COLUMN_CONTENT, when (element) {
                    is ArticleElement.Text -> element.content
                    is ArticleElement.Image -> element.imagePath
                })
            }
            db.insert(TABLE_ELEMENTS, null, elementValues)
        }

        return articleId
    }

    fun getAllArticles(): List<Article> {
        val db = readableDatabase
        val articles = mutableListOf<Article>()

        val cursor = db.query(TABLE_ARTICLES, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))

                val elements = getElementsForArticle(id)
                articles.add(Article(id, title, date, elements))
            } while (cursor.moveToNext())
        }
        cursor.close()

        return articles
    }

    fun getArticleById(articleId: Long): Article? {
        val db = readableDatabase
        var article: Article? = null

        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(articleId.toString())
        val cursor = db.query(TABLE_ARTICLES, null, selection, selectionArgs, null, null, null)
        if (cursor.moveToFirst()) {
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))

            val elements = getElementsForArticle(articleId)
            article = Article(articleId, title, date, elements)
        }
        cursor.close()

        return article
    }





    private fun getElementsForArticle(articleId: Long): List<ArticleElement> {
        val db = readableDatabase
        val elements = mutableListOf<ArticleElement>()

        val selection = "$COLUMN_ARTICLE_ID = ?"
        val selectionArgs = arrayOf(articleId.toString())
        val cursor = db.query(TABLE_ELEMENTS, null, selection, selectionArgs, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE))
                val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
                when (type) {
                    "text" -> elements.add(ArticleElement.Text(content))
                    "image" -> elements.add(ArticleElement.Image(content))
                }
            } while (cursor.moveToNext())
        }
        cursor.close()

        return elements
    }
}