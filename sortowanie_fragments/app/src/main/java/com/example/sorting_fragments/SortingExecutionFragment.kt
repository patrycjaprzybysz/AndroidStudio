package com.example.sorting_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class SortingExecutionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sorting_execution, container, false)

        val sortMethodDescription = view.findViewById<TextView>(R.id.sortMethodDescription)
        val initialSequence = view.findViewById<TextView>(R.id.initialSequence)
        val sortedSequence = view.findViewById<TextView>(R.id.sortedSequence)
        val backButton = view.findViewById<Button>(R.id.backButton)

        val numbers = arguments?.getString("numbers")!!.split(",").map { it.trim().toInt() }.toMutableList()
        val originalNumbers = numbers.toList()
        val method = arguments?.getString("method")!!

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
        initialSequence.text = "Initial Sequence: ${originalNumbers.joinToString(", ")}"
        sortedSequence.text = "Sorted Sequence: ${sortedNumbers.joinToString(", ")}"

        backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        return view
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