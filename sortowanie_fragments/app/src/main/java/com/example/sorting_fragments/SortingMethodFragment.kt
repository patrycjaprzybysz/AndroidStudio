package com.example.sorting_fragments



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment

class SortingMethodFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sorting_method, container, false)

        val inputNumbers = view.findViewById<EditText>(R.id.inputNumbers)
        val sortMethodSpinner = view.findViewById<Spinner>(R.id.sortMethodSpinner)
        val nextButton = view.findViewById<Button>(R.id.nextButton)


        val sortMethods = arrayOf("Bubble Sort", "Selection Sort", "Insertion Sort")
        sortMethodSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            sortMethods
        )

        nextButton.setOnClickListener {
            val numbers = inputNumbers.text.toString()
            val selectedMethod = sortMethodSpinner.selectedItem.toString()

            val bundle = Bundle().apply {
                putString("numbers", numbers)
                putString("method", selectedMethod)
            }

            val sortingExecutionFragment = SortingExecutionFragment().apply {
                arguments = bundle
            }

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, sortingExecutionFragment)
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}