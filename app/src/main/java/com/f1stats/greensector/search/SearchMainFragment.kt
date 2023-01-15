package com.f1stats.greensector.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.f1stats.greensector.R

class SearchMainFragment : Fragment() {
    private lateinit var progressBar: ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search_main, container, false)

        view.findViewById<Button>(R.id.search_circuit_fastest_lap).setOnClickListener { this.searchButtonClick(it) } // Send to Circuit Select
        view.findViewById<Button>(R.id.search_circuit_longest_lap).setOnClickListener { this.searchButtonClick(it) }
        view.findViewById<Button>(R.id.search_circuit_fastest_overall).setOnClickListener { this.searchButtonClick(it) }
        view.findViewById<Button>(R.id.search_circuit_longest_race).setOnClickListener { this.searchButtonClick(it) }
        view.findViewById<Button>(R.id.search_driver_wins).setOnClickListener { this.searchButtonClick(it) }
        view.findViewById<Button>(R.id.search_driver_podiums).setOnClickListener { this.searchButtonClick(it) }
        view.findViewById<Button>(R.id.search_driver_poles).setOnClickListener { this.searchButtonClick(it) }
        view.findViewById<Button>(R.id.search_driver_fastest_lap).setOnClickListener { this.searchButtonClick(it) }
        view.findViewById<Button>(R.id.search_driver_races).setOnClickListener { this.searchButtonClick(it) }
        view.findViewById<Button>(R.id.search_driver_standings).setOnClickListener { this.searchButtonClick(it) } // Send to Circuit Select
        view.findViewById<Button>(R.id.search_driver_avg_points).setOnClickListener { this.searchButtonClick(it) } // Send to Asc Desc Select
        view.findViewById<Button>(R.id.search_driver_avg_position).setOnClickListener { this.searchButtonClick(it) } // Send to Asc Desc Select
        view.findViewById<Button>(R.id.search_driver_top_points).setOnClickListener { this.searchButtonClick(it) } // Send to Driver Select
        view.findViewById<Button>(R.id.search_driver_top_finishes).setOnClickListener { this.searchButtonClick(it) } // Send to Driver Select
        view.findViewById<Button>(R.id.fastest_lap_update).setOnClickListener { this.searchButtonClick(it) } // Send to Update Fragment

        // Add progress bar
        progressBar = view.findViewById(R.id.progressbar_spinner_search)

        return view
    }

    private fun searchButtonClick(view: View) {
        // Display progress bar
        progressBar.visibility = View.VISIBLE

        // We never hide the progress as another fragment covers it and then using the fragments button up
        // reverts this view to a state before the progress bar is visible. So it works just fine.

        val searchId = view.tag.toString().toInt()
        println("search ID on button click: $searchId")

        // Make args bundle for fragment
        val args = Bundle()
        args.putInt("searchId", searchId)

        // Send ID to fragment_search
        when (searchId) {
            1, 10 -> {
                Navigation.findNavController(view).navigate(R.id.show_search_circuit_select, args)
            }
            11, 12 -> {
                Navigation.findNavController(view).navigate(R.id.show_search_asc_desc, args)
            }
            13, 14, 15 -> {
                Navigation.findNavController(view).navigate(R.id.show_search_driver_select, args)
            }
            else -> {
                Navigation.findNavController(view).navigate(R.id.show_search_result, args)
            }
        }
    }
}