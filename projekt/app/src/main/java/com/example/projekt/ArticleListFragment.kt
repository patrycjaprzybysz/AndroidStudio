package com.example.projekt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ArticleListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewArticles)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val articles = listOf(
            ArticlePreview(
                1L,
                "Łukasz Kobus nowym sekretarzem i dyrektorem Urzędu Miasta Gdyni",
                R.drawable.artykul1
            ),
            ArticlePreview(
                2L,
                "10 ton w jeden weekend – kto śmieci na gdyńskich plażach?",
                R.drawable.artykul2
            )
        )

        val adapter = ArticleAdapter(articles) { article ->
            val bundle = Bundle()
            bundle.putLong("articleId", article.id)
            val fragment = ArticleDetailFragment()
            fragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        recyclerView.adapter = adapter

        return view
    }
}