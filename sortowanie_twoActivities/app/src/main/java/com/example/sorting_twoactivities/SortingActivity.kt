package com.example.sorting_twoactivities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SortingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sorting)

        val sortMethodDescription = findViewById<TextView>(R.id.sortMethodDescription)
        val initialSequence = findViewById<TextView>(R.id.initialSequence)
        val sortedSequence = findViewById<TextView>(R.id.sortedSequence)

        val numbers = intent.getStringExtra("numbers")!!.split(",").map { it.trim().toInt() }.toMutableList()
        val originalNumbers = intent.getStringExtra("originalNumbers") // Pobieramy oryginalną sekwencję z Intent

        val method = intent.getStringExtra("method")!!

        val sortedNumbers = when (method) {
            "Bubble Sort" -> bubbleSort(numbers)
            "Selection Sort" -> selectionSort(numbers)
            "Insertion Sort" -> insertionSort(numbers)
            else -> numbers
        }

        val description = when (method) {
            "Bubble Sort" -> "Bubble Sort: Repeatedly swap adjacent elements if they are in wrong order."
            "Selection Sort" -> "Selection Sort: Select the smallest element from an unsorted list and swap it with the leftmost unsorted element."
            "Insertion Sort" -> "Insertion Sort: Insert each element into its proper place in the sorted part of the list."
            else -> ""
        }


        sortMethodDescription.text = description
        initialSequence.text = "Initial Sequence: ${originalNumbers ?: numbers.joinToString(", ")}"
        sortedSequence.text = "Sorted Sequence: ${sortedNumbers.joinToString(", ")}"

        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            goBack()
        }
    }

    private fun goBack() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun bubbleSort(list: MutableList<Int>): List<Int> {
        for (i in list.indices) {
            for (j in 0 until list.size - i - 1) {
                if (list[j] > list[j + 1]) {
                    val temp = list[j]
                    list[j] = list[j + 1]
                    list[j + 1] = temp
                }
            }
        }
        return list
    }

    private fun selectionSort(list: MutableList<Int>): List<Int> {
        for (i in list.indices) {
            var minIdx = i
            for (j in i + 1 until list.size) {
                if (list[j] < list[minIdx]) {
                    minIdx = j
                }
            }
            val temp = list[i]
            list[i] = list[minIdx]
            list[minIdx] = temp
        }
        return list
    }

    private fun insertionSort(list: MutableList<Int>): List<Int> {
        for (i in 1 until list.size) {
            val key = list[i]
            var j = i - 1
            while (j >= 0 && list[j] > key) {
                list[j + 1] = list[j]
                j -= 1
            }
            list[j + 1] = key
        }
        return list
    }
}
