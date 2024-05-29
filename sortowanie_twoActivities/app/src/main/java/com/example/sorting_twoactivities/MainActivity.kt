package com.example.sorting_twoactivities


import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputNumbers = findViewById<EditText>(R.id.inputNumbers)
        val sortMethodSpinner = findViewById<Spinner>(R.id.sortMethodSpinner)
        val nextButton = findViewById<Button>(R.id.nextButton)

        val sortMethods = arrayOf("Bubble Sort", "Selection Sort", "Insertion Sort")
        sortMethodSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, sortMethods)

        nextButton.setOnClickListener {
            val numbers = inputNumbers.text.toString()
            val selectedMethod = sortMethodSpinner.selectedItem.toString()


            val originalNumbers = inputNumbers.text.toString()

            val intent = Intent(this, SortingActivity::class.java).apply {
                putExtra("numbers", numbers)
                putExtra("method", selectedMethod)


                putExtra("originalNumbers", originalNumbers)
            }
            startActivity(intent)
        }
    }
}
