package com.f1stats.greensector.circuit

import android.content.res.Resources
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

private const val TAG = "CircuitsFragment"

class CircuitsFragment : Fragment() {
    private lateinit var circuitPicture: ImageView
    private lateinit var drawable: Drawable
    private lateinit var circuitNameView: TextView
    private lateinit var circuitLenView: TextView
    private lateinit var lapCountView: TextView
    private lateinit var circuitDistView: TextView
    private lateinit var numTurnsView: TextView
    private lateinit var lapRecordView: TextView
    private lateinit var leftTurnCountView: TextView
    private lateinit var firstGPView: TextView
    private lateinit var rightTurnCountView: TextView
    private var trackID: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_circuits, container, false)

        arguments?.let { this.trackID = it.getInt("track_id") } // get track ID

        println("track ID from track model: ${this.trackID}")

        // initialize views for modification
        this.circuitPicture = view.findViewById(R.id.circuit_image)
        this.circuitNameView = view.findViewById(R.id.circuit_title)
        this.circuitLenView = view.findViewById(R.id.circuit_length_val)
        this.lapCountView = view.findViewById(R.id.lap_count_val)
        this.circuitDistView = view.findViewById(R.id.circuit_dist_val)
        this.numTurnsView = view.findViewById(R.id.num_turns_val)
        this.lapRecordView = view.findViewById(R.id.circuit_record_val)
        this.leftTurnCountView = view.findViewById(R.id.left_turns_val)
        this.firstGPView = view.findViewById(R.id.circuit_first_gp_val)
        this.rightTurnCountView = view.findViewById(R.id.right_turns_value)

        this.getCircuitInfo()
        return view
    }

    private fun getCircuitInfo() {
        val circuitInfo = CircuitsModel(this.trackID)

        this.circuitNameView.text = getString(R.string.circuit_title, circuitInfo.circuitName)

        try {
            var circuitPicture = circuitInfo.circuitName.lowercase()

            // Getting String into a char array of the length of the string to then replace all
            // spaces and dashes with a underscore as to get the drawable
            val temp = CharArray(circuitPicture.length)
            var counter = 0

            for(i in circuitPicture.iterator()) {
                if(i == '-' || i == ' ') {
                    temp[counter] = '_'
                } else {
                    temp[counter] = i
                }
                counter++
            }
            circuitPicture = String(temp)

            val uri = "@drawable/$circuitPicture"
            val imageResource = getResources().getIdentifier(uri,
                null, "com.f1stats.greensector")
            this.drawable = getResources().getDrawable(imageResource)
            this.circuitPicture.setImageDrawable(this.drawable)
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Could not find $circuitPicture drawable")
        }

        this.circuitLenView.text = getString(R.string.circuit_length_value, circuitInfo.lapLength)
        this.lapCountView.text = getString(R.string.lap_count_val, circuitInfo.lapCount)
        this.circuitDistView.text = getString(R.string.circuit_distance_value, circuitInfo.distance)
        this.numTurnsView.text = getString(R.string.num_turns_value, circuitInfo.numTurns)
        this.lapRecordView.text = getString(R.string.lap_record_value, circuitInfo.lapRecord)
        this.leftTurnCountView.text = getString(R.string.left_turns_value, circuitInfo.leftTurns)
        this.firstGPView.text = getString(R.string.first_gp_value, circuitInfo.firstGP)
        this.rightTurnCountView.text = getString(R.string.right_turns_val, circuitInfo.rightTurns)


    }
}