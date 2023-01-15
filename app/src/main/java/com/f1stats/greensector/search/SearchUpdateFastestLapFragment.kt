package com.f1stats.greensector.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.f1stats.greensector.R
import com.f1stats.greensector.search.serial.SerialSearchFastestLap
import com.f1stats.greensector.search.serial.SerialSearchResultLongestLap
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.floor

class SearchUpdateFastestLapFragment : Fragment() {
    private lateinit var lapEditText: EditText
    private lateinit var lapTimeEditText: EditText
    private lateinit var lapTimeValue: TextView
    private lateinit var lapValue: TextView
    private var driverId = 1
    private var raceId = 1
    private var searchId = 1
    private lateinit var thisContext: Context
    private var lap = -1
    private var lapTime = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (container != null) {
            thisContext = container.context
        }

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search_update_fastest_lap, container, false)

        arguments?.let { this.searchId = it.getInt("searchId")} // Get search Id
        println(searchId)

        // Get race and driver id's
        val temp = floor(searchId.toDouble()/100).toInt()
        raceId = searchId - (temp * 100)
        val temp2 = floor(temp.toDouble()/100).toInt()
        driverId = temp - (temp2 * 100)


        view.findViewById<Button>(R.id.update_bt).setOnClickListener { this.updateButtonClick(view) }
        view.findViewById<Button>(R.id.add_bt).setOnClickListener { this.addButtonClick(view) }
        view.findViewById<Button>(R.id.remove_bt).setOnClickListener { this.removeButtonClick(view) }
        this.lapEditText = view.findViewById(R.id.update_lap_edit)
        this.lapTimeEditText = view.findViewById(R.id.update_lap_time_edit)
        this.lapValue = view.findViewById(R.id.lap_value)
        this.lapTimeValue = view.findViewById(R.id.lap_time_value)

        functionCallForValue()

        this.lapTimeValue.text = lapTime
        if (lap != -1) {
            this.lapValue.text = lap.toString()
        } else {
            this.lapValue.text = ""
        }

        return view
    }


    private fun updateButtonClick(view: View) {
        if (lapTime == "" && lap == -1) {
            Toast.makeText(thisContext, "Unable to update fastest lap, it doesn't exist", Toast.LENGTH_SHORT).show()
        } else {
            functionCallForUpdate()
            Toast.makeText(thisContext, "Updated drivers fastest lap time", Toast.LENGTH_SHORT)
                .show()
            Navigation.findNavController(view).navigate(R.id.update_to_search_home)
        }
    }

    private fun addButtonClick(view: View) {
        if (lapTime != "" && lap != -1) {
            Toast.makeText(thisContext, "Unable to add rows, they exist already", Toast.LENGTH_SHORT).show()
        } else {
            functionCallForAdd()
            Toast.makeText(thisContext, "Added new fastest lap time", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(view).navigate(R.id.update_to_search_home)
        }
    }

    private fun removeButtonClick(view: View) {
        if (lapTime == "" && lap == -1) {
            Toast.makeText(thisContext, "Unable to delete rows, they don't exist", Toast.LENGTH_SHORT).show()
        } else {
            functionCallForRemove()
            Toast.makeText(thisContext, "Removed fastest lap times", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(view).navigate(R.id.update_to_search_home)
        }
    }

    private fun createConnection(): SupabaseClient {
        val client =  createSupabaseClient(
            supabaseUrl = "https://dnknowgkpotzpxmcpvbe.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImRua25vd2drcG90enB4bWNwdmJlIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzEyNDQ2OTksImV4cCI6MTk4NjgyMDY5OX0.RakVDrvPjI8nQaQ_aLXDcRaPFodeLR7Q2vvf9lb5Np0"
        ) {
            install(Postgrest)
        }
        return client
    }

    private suspend fun updateDatabase() {
        try {
            val client = createConnection()
            // Make Update
            client.postgrest["fastestlaptime"]
                .update(
                    {
                        set("lap", lapEditText.text.toString().toInt())
                        set("lap_time", lapTimeEditText.text.toString().toDouble())
                    }
                ) {
                    eq("driver_id", driverId)
                    eq("race_id", raceId)
                }
            client.close()
        } catch (e: RestException) {
            println("Unable to update")
        }

    }

    private suspend fun getValues(): List<SerialSearchFastestLap> {
        val serializedResult: List<SerialSearchFastestLap> = try {
            val client = createConnection()
            // Get current times, if applicable
            val result = client.postgrest["fastestlaptime"]
                .select {
                    eq("driver_id", driverId)
                    eq("race_id", raceId)
                }
            println(result)
            result.decodeList()
        } catch (e: RestException) {
            println("Unable to make call")
            emptyList()
        }
        return serializedResult
    }

    private suspend fun addValues() {
        try {
            val client = createConnection()
            // Add a row to the table
            client.postgrest["fastestlaptime"]
                .insert(SerialSearchFastestLap(driverId, raceId, lapEditText.text.toString().toInt(), lapTimeEditText.text.toString().toFloat()))
        } catch (e: RestException) {
            println("Unable to insert")
        }
    }

    private suspend fun removeValues() {
        try {
            val client = createConnection()
            // Remove a row from the table
            client.postgrest["fastestlaptime"]
                .delete {
                    SerialSearchFastestLap::driverId eq driverId
                    SerialSearchFastestLap::raceId eq raceId
                }
        } catch (e: RestException) {
            println("Unable to remove")
        }
    }

    private fun functionCallForUpdate(){
        val updateJob = CoroutineScope(Dispatchers.IO).launch {
            updateDatabase()
        }
    }

    private fun functionCallForValue(){
        val findJob = CoroutineScope(Dispatchers.IO).launch {
            try {
                val serialResult = getValues()
                lap = serialResult[0].lap
                lapTime = lapTimeToTimeFormat(serialResult[0].lapTime.toDouble())
            } catch (e: java.lang.IndexOutOfBoundsException) {
                println("Entry doesn't exist")
            }
        }

        while(!findJob.status())
        {
            Thread.sleep(250)
        }
        Thread.sleep(250)
    }

    private fun functionCallForAdd() {
        val addJob = CoroutineScope(Dispatchers.IO).launch {
            addValues()
        }
    }

    private fun functionCallForRemove() {
        val removeJob = CoroutineScope(Dispatchers.IO).launch {
            removeValues()
        }
    }

    private fun Job.status(): Boolean = when {
        isCompleted -> true
        else -> false
    }

    private fun lapTimeToTimeFormat(double: Double): String {
        var timeString = ""
        val mins = floor((double / 60))
        val secAndMs = double % 60
        val secAndMsDecimal = BigDecimal(secAndMs).setScale(3, RoundingMode.HALF_EVEN)
        val decimalCompare = BigDecimal(10)

        timeString += mins.toInt()
        timeString += ":"

        /*  WHEN EXPRESSION
         * This when expression adds a 0 in front of BigDecimal values less than 10 as to make
         * the time output string make more sense
         * e.g. if BigDecimal equals 5.043 with out the when the output string would be 1:5.043
         * Which does not make sense for time, so with the when it would be 1:05.043 which makes more sense
         */
        when (secAndMsDecimal.compareTo(decimalCompare)) {
            -1 -> timeString += "0"
            else -> 0
        }
        timeString += secAndMsDecimal
        return timeString
    }
}