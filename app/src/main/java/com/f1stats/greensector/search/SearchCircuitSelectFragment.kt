package com.f1stats.greensector.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.navigation.Navigation
import com.f1stats.greensector.R

class SearchCircuitSelectFragment : Fragment() {
    private lateinit var progressBar: ProgressBar
    private var searchId: Int = 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search_circuit_select, container, false)

        arguments?.let { this.searchId = it.getInt("searchId")} // Get search Id

        // add button listener to all buttons
        view.findViewById<Button>(R.id.bahrain_button).setOnClickListener { this.trackButtonClick(it) }
        view.findViewById<Button>(R.id.jeddah_button).setOnClickListener { this.trackButtonClick(it) }
        view.findViewById<Button>(R.id.australia_button).setOnClickListener { this.trackButtonClick(it) }
        view.findViewById<Button>(R.id.emilia_button).setOnClickListener { this.trackButtonClick(it) }
        view.findViewById<Button>(R.id.miami_button).setOnClickListener { this.trackButtonClick(it) }
        view.findViewById<Button>(R.id.barcelona_button).setOnClickListener { this.trackButtonClick(it) }
        view.findViewById<Button>(R.id.monaco_button).setOnClickListener { this.trackButtonClick(it) }
        view.findViewById<Button>(R.id.azerbaijan_button).setOnClickListener { this.trackButtonClick(it) }
        view.findViewById<Button>(R.id.canada_button).setOnClickListener { this.trackButtonClick(it) }
        view.findViewById<Button>(R.id.silverstone_button).setOnClickListener { this.trackButtonClick(it) }
        view.findViewById<Button>(R.id.red_bull_button).setOnClickListener { this.trackButtonClick(it) }
        view.findViewById<Button>(R.id.france_button).setOnClickListener { this.trackButtonClick(it) }
        view.findViewById<Button>(R.id.hungary_button).setOnClickListener { this.trackButtonClick(it) }
        view.findViewById<Button>(R.id.spa_button).setOnClickListener { this.trackButtonClick(it) }
        view.findViewById<Button>(R.id.zandvoort_button).setOnClickListener { this.trackButtonClick(it) }
        view.findViewById<Button>(R.id.monza_button).setOnClickListener { this.trackButtonClick(it) }
        view.findViewById<Button>(R.id.singapore_button).setOnClickListener { this.trackButtonClick(it) }
        view.findViewById<Button>(R.id.suzuka_button).setOnClickListener { this.trackButtonClick(it) }
        view.findViewById<Button>(R.id.cota_button).setOnClickListener { this.trackButtonClick(it) }
        view.findViewById<Button>(R.id.mexico_button).setOnClickListener { this.trackButtonClick(it) }
        view.findViewById<Button>(R.id.sao_paulo_button).setOnClickListener { this.trackButtonClick(it) }
        view.findViewById<Button>(R.id.abu_dhabi_button).setOnClickListener { this.trackButtonClick(it) }

        // Add progress bar
        progressBar = view.findViewById(R.id.progressbar_spinner)

        return view
    }

    private fun trackButtonClick(view: View) {
        // Display progress bar
        progressBar.visibility = View.VISIBLE

        var searchCircuit = searchId * 100

        searchCircuit += view.tag.toString().toInt()
        println("SearchCircuit on click is $searchCircuit")

        // Make args bundle for fragment
        val args = Bundle()
        args.putInt("searchId", searchCircuit)

        if (searchCircuit >= 150000) {
            // Send ID to search_update_fastest_lap_fragment
            Navigation.findNavController(view).navigate(R.id.search_circuit_to_update, args)
        } else {
            // Send ID to search_results_fragment
            Navigation.findNavController(view).navigate(R.id.search_circuit_to_detail, args)
        }
    }

}