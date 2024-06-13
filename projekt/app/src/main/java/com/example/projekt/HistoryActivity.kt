package com.example.projekt

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.view.Gravity
import android.graphics.Typeface

class HistoryActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        dbHelper = DatabaseHelper(this)

        val textViewMain: TextView = findViewById(R.id.textViewMain)
        textViewMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val textViewTourism: TextView = findViewById(R.id.textViewTourism)
        textViewTourism.setOnClickListener {
            val intent = Intent(this, TourismActivity::class.java)
            startActivity(intent)
        }

        val articles = dbHelper.getAllArticles()
        val article = articles.firstOrNull() // Pobierz pierwszy artykuł jako przykład
//        val article = articles.getOrNull(1)
        val container = findViewById<LinearLayout>(R.id.container)

        article?.let {
            // Dodaj tytuł artykułu
            val titleTextView = TextView(this).apply {
                text = it.title
                textSize = 24f
                setTypeface(null, Typeface.BOLD)
                setPadding(10, 20, 10, 0)
            }
            container.addView(titleTextView)

            // Dodaj datę artykułu
            val dateTextView = TextView(this).apply {
                text = it.date
                textSize = 14f
                setPadding(10, 5, 10, 20)
            }
            container.addView(dateTextView)

            // Dodaj zawartość artykułu
            it.elements.forEach { element ->
                when (element) {
                    is ArticleElement.Text -> {
                        val textView = TextView(this).apply {
                            text = element.content
                            textSize = 18f
                            setPadding(10, 25, 10, 0)
                            // Przykładowa zmiana stylu, jeśli tekst zawiera określony tekst
                            if (element.content.startsWith("Odzyskanie przez Polskę")) {
                                setTypeface(null, Typeface.BOLD)
                            }
                        }
                        container.addView(textView)
                    }
                    is ArticleElement.Image -> {
                        val imageView = ImageView(this).apply {
                            setImageResource(resources.getIdentifier(element.imagePath, null, packageName))
                            layoutParams = LinearLayout.LayoutParams(
                                198.dpToPx(), // width
                                287.dpToPx() // height
                            ).apply {
                                gravity = Gravity.CENTER
                                setMargins(10, 10, 10, 10)
                            }
                            adjustViewBounds = true
                        }
                        container.addView(imageView)
                    }
                }
            }
        }
    }

    // Extension function to convert dp to pixels
    private fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }
}
