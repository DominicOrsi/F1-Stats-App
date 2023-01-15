package com.f1stats.greensector.driver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.f1stats.greensector.R

class SearchDriverSelectFragment : Fragment() {
    private lateinit var progressBar: ProgressBar
    private var searchId = 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_driver_select, container, false)

        arguments?.let { this.searchId = it.getInt("searchId")} // Get search Id

        // add button listener to all buttons
        view.findViewById<Button>(R.id.max_button).setOnClickListener { this.driverButtonClick(it) }
        view.findViewById<Button>(R.id.charles_button).setOnClickListener { this.driverButtonClick(it) }
        view.findViewById<Button>(R.id.sergio_button).setOnClickListener { this.driverButtonClick(it) }
        view.findViewById<Button>(R.id.george_button).setOnClickListener { this.driverButtonClick(it) }
        view.findViewById<Button>(R.id.carlos_button).setOnClickListener { this.driverButtonClick(it) }
        view.findViewById<Button>(R.id.lewis_button).setOnClickListener { this.driverButtonClick(it) }
        view.findViewById<Button>(R.id.lando_button).setOnClickListener { this.driverButtonClick(it) }
        view.findViewById<Button>(R.id.esteban_button).setOnClickListener { this.driverButtonClick(it) }
        view.findViewById<Button>(R.id.fernando_button).setOnClickListener { this.driverButtonClick(it) }
        view.findViewById<Button>(R.id.valtteri_button).setOnClickListener { this.driverButtonClick(it) }
        view.findViewById<Button>(R.id.daniel_button).setOnClickListener { this.driverButtonClick(it) }
        view.findViewById<Button>(R.id.sebastian_button).setOnClickListener { this.driverButtonClick(it) }
        view.findViewById<Button>(R.id.kevin_button).setOnClickListener { this.driverButtonClick(it) }
        view.findViewById<Button>(R.id.pierre_button).setOnClickListener { this.driverButtonClick(it) }
        view.findViewById<Button>(R.id.lance_button).setOnClickListener { this.driverButtonClick(it) }
        view.findViewById<Button>(R.id.mick_button).setOnClickListener { this.driverButtonClick(it) }
        view.findViewById<Button>(R.id.yuki_button).setOnClickListener { this.driverButtonClick(it) }
        view.findViewById<Button>(R.id.zhou_button).setOnClickListener { this.driverButtonClick(it) }
        view.findViewById<Button>(R.id.alexander_button).setOnClickListener { this.driverButtonClick(it) }
        view.findViewById<Button>(R.id.nicholas_button).setOnClickListener { this.driverButtonClick(it) }

        // Add progress bar
        progressBar = view.findViewById(R.id.progressbar_spinner)

        return view
    }

    private fun driverButtonClick(view: View) {
        // Display progress bar
        progressBar.visibility = View.VISIBLE

        var searchDriver = searchId * 100

        searchDriver += view.tag.toString().toInt()
        println("SearchDriver on click is $searchDriver")

        // Make args bundle for fragment
        val args = Bundle()
        args.putInt("searchId", searchDriver)

        if (searchId == 15) {
            // Send ID to search_circuit_fragment
            Navigation.findNavController(view).navigate(R.id.search_driver_to_circuit, args)
        } else {
            // Send ID to search_results_fragment
            Navigation.findNavController(view).navigate(R.id.search_driver_to_detail, args)
        }
    }
}
