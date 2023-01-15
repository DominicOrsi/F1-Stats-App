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


class SearchAscOrDesc : Fragment() {
    private lateinit var progressBar: ProgressBar
    private var searchId: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search_asc_or_desc, container, false)

        arguments?.let { this.searchId = it.getInt("searchId")} // Get search Id

        view.findViewById<Button>(R.id.asc_button).setOnClickListener { this.topOrBottomButtonClick(it) }
        view.findViewById<Button>(R.id.desc_button).setOnClickListener { this.topOrBottomButtonClick(it) }

        // Add progress bar
        progressBar = view.findViewById(R.id.progressbar_spinner_top_bottom)

        return view
    }

    private fun topOrBottomButtonClick(view: View) {
        // Display progress bar
        progressBar.visibility = View.VISIBLE

        var searchAscOrDesc = searchId * 100

        searchAscOrDesc += view.tag.toString().toInt()
        println("searchAscOrDesc on click is $searchAscOrDesc")

        // Make args bundle for fragment
        val args = Bundle()
        args.putInt("searchId", searchAscOrDesc)

        // Send ID to fragment_circuits
        Navigation.findNavController(view).navigate(R.id.search_asc_to_detail, args)
    }
}