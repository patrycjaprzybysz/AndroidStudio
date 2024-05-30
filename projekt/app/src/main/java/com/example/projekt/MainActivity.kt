package com.example.projekt

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textViewHistory: TextView = findViewById(R.id.textViewHistory)
        textViewHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        val textViewTourism: TextView = findViewById(R.id.textViewTourism)
        textViewTourism.setOnClickListener {
            val intent = Intent(this, TourismActivity::class.java)
            startActivity(intent)
        }
    }
}
