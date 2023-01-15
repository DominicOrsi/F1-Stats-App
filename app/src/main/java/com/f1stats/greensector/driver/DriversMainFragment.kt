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

class DriversMainFragment : Fragment() {
    private lateinit var progressBar: ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_drivers_main, container, false)

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

        // We never hide the progress as another fragment covers it and then using the fragments button up
        // reverts this view to a state before the progress bar is visible. So it works just fine.

        val driverId = view.tag.toString().toInt()
        println("driver ID on button click: $driverId")

        // Make args bundle for fragment
        val args = Bundle()
        args.putInt("driver_id", driverId)

        // Send ID to fragment_circuits
        Navigation.findNavController(view).navigate(R.id.show_driver_detail, args)
    }
}

