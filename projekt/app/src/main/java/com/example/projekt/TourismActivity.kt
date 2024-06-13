package com.example.projekt

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class TourismActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tourism)


        val textViewHistory: TextView = findViewById(R.id.textViewHistory)
        textViewHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        val textViewMain: TextView = findViewById(R.id.textViewMain)
        textViewMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        val checkAttractionsButton: Button = findViewById(R.id.checkAttractionsButton)
        checkAttractionsButton.setOnClickListener {
            openMap()
        }
    }
    private fun openMap() {
        val mapUrl = "https://smartmapa.um.gdynia.pl/pl/app/inwestycje" // Tutaj wstaw link do strony z mapą
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl))
        startActivity(intent)
    }




}