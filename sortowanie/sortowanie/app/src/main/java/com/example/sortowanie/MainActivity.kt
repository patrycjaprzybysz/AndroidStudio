package com.example.sortowanie

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var sortButton: Button
    private lateinit var sortSpinner: Spinner
    private lateinit var sortedTextView: TextView
    private val numbersList = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        sortButton = findViewById(R.id.sortButton)
        sortSpinner = findViewById(R.id.sortSpinner)
        sortedTextView = findViewById(R.id.sortedTextView)

        val sortMethods = resources.getStringArray(R.array.sort_methods)

        val sortAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sortMethods)
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sortSpinner.adapter = sortAdapter

        sortButton.setOnClickListener {
            addNumberToList()
            sortNumbers()
        }

        sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                sortNumbers()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun addNumberToList() {
        val input = editText.text.toString()
        val numbers = input.split("\\s+".toRegex())
        for (numberStr in numbers) {
            if (numberStr.isNotEmpty()) {
                try {
                    val number = numberStr.toInt()
                    numbersList.add(number)
                } catch (e: NumberFormatException) {
                    editText.error = "Wprowadź liczby całkowite oddzielone spacjami"
                    return
                }
            }
        }
        editText.text.clear()
    }

    private fun sortNumbers() {
        val sortMethod = sortSpinner.selectedItem.toString()
        val sortedNumbers = when (sortMethod) {
            "Sortowanie bąbelkowe" -> bubbleSort(numbersList.toList())
            "Sortowanie przez wstawianie" -> insertionSort(numbersList.toList())
            else -> listOf()
        }
        sortedTextView.text = "Posortowane liczby: ${sortedNumbers.joinToString(", ")}"
    }

    private fun bubbleSort(list: List<Int>): List<Int> {
        val sortedList = list.toMutableList()
        for (i in 0 until sortedList.size - 1) {
            for (j in 0 until sortedList.size - i - 1) {
                if (sortedList[j] > sortedList[j + 1]) {
                    val temp = sortedList[j]
                    sortedList[j] = sortedList[j + 1]
                    sortedList[j + 1] = temp
                }
            }
        }
        return sortedList
    }

    private fun insertionSort(list: List<Int>): List<Int> {
        val sortedList = list.toMutableList()
        for (i in 1 until sortedList.size) {
            val key = sortedList[i]
            var j = i - 1
            while (j >= 0 && sortedList[j] > key) {
                sortedList[j + 1] = sortedList[j]
                j--
            }
            sortedList[j + 1] = key
        }
        return sortedList
    }
}