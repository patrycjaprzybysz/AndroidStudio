package com.example.projekt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class ArticleDetailFragment : Fragment() {
    private lateinit var dbHelper: DatabaseHelper
    private var articleId: Long = -1L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_article_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DatabaseHelper(requireContext())

        val articleId = arguments?.getLong("ARTICLE_ID", -1)
        val article = articleId?.let { dbHelper.getArticleById(it) }

        // Ustawianie danych artykułu w widoku
        article?.let { displayArticle(it) }
    }

    private fun displayArticle(article: Article) {
        val titleTextView: TextView = requireView().findViewById(R.id.textViewTitle)
        val imageViewArticle: ImageView = requireView().findViewById(R.id.imageViewArticle)
        val textTextView: TextView = requireView().findViewById(R.id.text)

        titleTextView.text = article.title

        // Wyświetlanie obrazu artykułu
        if (article.elements.isNotEmpty()) {
            val firstElement = article.elements[0]
            if (firstElement is ArticleElement.Image) {
                // Pobranie identyfikatora zasobu obrazu
                val res = resources // Pobranie obiektu Resources
                val packageName = requireContext().packageName // Pobranie nazwy pakietu aplikacji
                val imageIdentifier = res.getIdentifier(firstElement.imagePath, "drawable", packageName) // Pobranie identyfikatora zasobu obrazu

                // Sprawdzenie, czy identyfikator zasobu został znaleziony
                if (imageIdentifier != 0) {
                    // Jeśli identyfikator zasobu został znaleziony, ustaw go w ImageView
                    imageViewArticle.setImageResource(imageIdentifier)
                } else {
                    // Jeśli identyfikator zasobu nie został znaleziony, możesz ustawić domyślny obrazek lub inaczej obsłużyć ten przypadek
                    imageViewArticle.setImageResource(R.drawable.default_image)
                }
            }
        }

        // Wyświetlanie tekstu artykułu
        val textContent = StringBuilder()
        for (element in article.elements) {
            if (element is ArticleElement.Text) {
                textContent.append(element.content)
                textContent.append("\n")
            }
        }
        textTextView.text = textContent.toString()
    }

}
