package com.f1stats.greensector.driver

import android.content.res.Resources.NotFoundException
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.f1stats.greensector.R

private const val TAG = "DriversFragment"

class DriversFragment : Fragment() {

    private lateinit var driverPicture: ImageView
    private lateinit var drawable: Drawable
    private lateinit var driverNameView: TextView
    private lateinit var driverNumberView: TextView
    private lateinit var driverCareerWinsView: TextView
    private lateinit var driverCareerPodiumsView: TextView
    private lateinit var driverCareerFastestView: TextView
    private lateinit var driverCareerGPView: TextView
    private lateinit var driverSeasonWinsView: TextView
    private lateinit var driverSeasonPodiumsView: TextView
    private lateinit var driverSeasonFastestView: TextView
    private lateinit var driverSeasonGPView: TextView
    private lateinit var driverDOBView: TextView
    private lateinit var driverNationalityView: TextView
    private var driverId: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_drivers, container, false)

        arguments?.let { this.driverId = it.getInt("driver_id")} // Get driver Id

        driverPicture = view.findViewById(R.id.driver_image)
        driverNameView = view.findViewById(R.id.driver_name)
        driverNumberView = view.findViewById(R.id.driver_number)

        // Career View Finds
        driverCareerWinsView = view.findViewById(R.id.career_wins)
        driverCareerPodiumsView = view.findViewById(R.id.career_podiums)
        driverCareerFastestView = view.findViewById(R.id.career_fastest_laps)
        driverCareerGPView = view.findViewById(R.id.career_gps)

        // Season View Finds
        driverSeasonWinsView = view.findViewById(R.id.season_wins)
        driverSeasonPodiumsView = view.findViewById(R.id.season_podiums)
        driverSeasonFastestView = view.findViewById(R.id.season_fastest_laps)
        driverSeasonGPView = view.findViewById(R.id.season_gps)

        // DOB and Nationality Finds
        driverDOBView = view.findViewById(R.id.driver_dob)
        driverNationalityView = view.findViewById(R.id.driver_nationality)

        getDriverInfo()
        return view
    }


    private fun getDriverInfo() {
        val driverInfo = DriversModel(this.driverId)

        /* Upper Section */
        // Getting drivers Name
        val driverFirstName = driverInfo.firstName
        val driverLastName = driverInfo.lastName.uppercase()
        val driverFullName = "$driverFirstName $driverLastName"
        // Getting Drivers Number
        val driverNumber = driverInfo.number

        val driverNameText = getString(R.string.driver_name, driverFullName)
        driverNameView.text = driverNameText
        val driverNumberText = getString(R.string.driver_number, driverNumber)
        driverNumberView.text = driverNumberText

        /* Lower Section */
        // Getting Career Stats
        val cWins = driverInfo.cWins
        val cPodiums = driverInfo.cPodiums
        val cFastestLap = driverInfo.cFastestLap
        val cGp = driverInfo.cGp

        val cWinsText = getString(R.string.career_wins, cWins)
        driverCareerWinsView.text = cWinsText
        val cPodiumsText = getString(R.string.career_podiums, cPodiums)
        driverCareerPodiumsView.text = cPodiumsText
        val cFastestLapText = getString(R.string.career_fastest_laps, cFastestLap)
        driverCareerFastestView.text = cFastestLapText
        val cGpText = getString(R.string.career_races, cGp)
        driverCareerGPView.text = cGpText


        // Getting Season Stats
        val sWins = driverInfo.sWins
        val sPodiums = driverInfo.sPodiums
        val sFastLap = driverInfo.sFastestLap
        val sGp = driverInfo.sGp

        val sWinsText = getString(R.string.season_wins, sWins)
        driverSeasonWinsView.text = sWinsText
        val sPodiumsText = getString(R.string.season_podiums, sPodiums)
        driverSeasonPodiumsView.text = sPodiumsText
        val sFastLapText = getString(R.string.season_fastest_laps, sFastLap)
        driverSeasonFastestView.text = sFastLapText
        val sGpText = getString(R.string.season_races, sGp)
        driverSeasonGPView.text = sGpText

        // Getting DOB and Nationality
        val dateOfBirth = driverInfo.dateOfBirth
        val nationality = driverInfo.nationality

        val dateOfBirthText = getString(R.string.dob, dateOfBirth)
        driverDOBView.text = dateOfBirthText
        val nationalityText = getString(R.string.nationality, nationality)
        driverNationalityView.text = nationalityText

        try {
            // Getting Picture
            val driverPictureName = driverLastName.lowercase()
            val uri = "@drawable/$driverPictureName"
            val imageResource = getResources().getIdentifier(uri, null, "com.f1stats.greensector")
            drawable = getResources().getDrawable(imageResource)    // I know this is depreciated but it works
            // can't find another way around this
            // Setting the drawable
            driverPicture.setImageDrawable(drawable)
        } catch (e: NotFoundException) {
            Log.e(TAG, "Could not find $driverLastName drawable")
        }

    }

}

