package com.example.projekt

sealed class ArticleElement {
    data class Text(val content: String) : ArticleElement()
    data class Image(val imagePath: String) : ArticleElement()
}

data class Article(
    val id: Long,
    val title: String,
    val date: String,
    val elements: List<ArticleElement>
)

