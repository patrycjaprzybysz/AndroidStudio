package com.example.projekt

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.projekt.databinding.ActivityHistoryBinding
import com.example.projekt.databinding.ActivityTourismBinding


class TourismActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTourismBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTourismBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.textViewMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.textViewHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
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