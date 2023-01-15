package com.f1stats.greensector.circuit

import android.util.Log
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


private const val TAG = "CircuitsModel"

class CircuitsModel constructor(circuitId: Int) {
    var circuitName = "ERROR"
    var lapLength = "0.0"
    var lapCount = 0
    var numTurns = 0
    var leftTurns = 0
    var rightTurns = 0
    var distance = "0.0"
    var lapRecord = "ERROR"
    var firstGP = 0

    init {
        this.getTrackData(circuitId)
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

    private suspend fun getCircuitInfo(circuitId: Int): List<SerialCircuitInfo> {
        val serializedResult: List<SerialCircuitInfo> = try {
            val client = createConnection()
            val result = client.postgrest["circuit"]
                .select {
                    eq("circuit_id", circuitId)
                }
            client.close()
            result.decodeList()
        } catch (e: RestException) {
            println("Unable to make call")
            emptyList()
        }
        return serializedResult
    }

    private suspend fun getCircuitFastLap(circuitId: Int): String {
        val lapString = try {
            val client = createConnection()
            val result = client.postgrest.rpc("get_circuit_fast_lap", mapOf("id" to circuitId))
            client.close()
            lapTimeDoubleToMinSecMsFormat(getLapTimeFromStringToDouble(result.toString()))
        } catch (e: RestException) {
            println("Unable to make call")
            println("Exception: $e")
            "" // Empty String to return
        }
        return lapString
    }

    private fun getTrackData(circuitId: Int) {
        val circuitInfoJob = CoroutineScope(Dispatchers.IO).launch{

            val circuitInfoResult = getCircuitInfo(circuitId)
            try {
                circuitName = circuitInfoResult[0].name
                lapLength = circuitInfoResult[0].lap_distance.toString() + " km"
                lapCount = circuitInfoResult[0].laps
                numTurns = circuitInfoResult[0].turns
                rightTurns = circuitInfoResult[0].right_turns
                leftTurns = circuitInfoResult[0].left_turns
                distance = circuitInfoResult[0].race_distance.toString() + " km"
                firstGP = circuitInfoResult[0].first_race
            } catch (e: java.lang.IndexOutOfBoundsException) {
                Log.e(TAG, "Driver Info unable to serialize")
            }
        }

        val circuitFastLapJob = CoroutineScope(Dispatchers.IO).launch {
            lapRecord = getCircuitFastLap(circuitId)
        }

        // This is such a bad way to check if the threads are done,
        // should try and use a promise and await()/awaitAll()
        while(!circuitInfoJob.status() && !circuitFastLapJob.status())
        {
            Thread.sleep(250)
        }
        Thread.sleep(250)
    }

    private fun Job.status(): Boolean = when {
        isCompleted -> true
        else -> false
    }

    private fun getLapTimeFromStringToDouble(string: String): Double {
        var index = 21
        var i = 0
        var newString = ""
        while (string[index] != ',') {
            newString += string[index]
            index += 1
            i += 1
        }
        return newString.toDouble()
    }

    private fun lapTimeDoubleToMinSecMsFormat(double: Double): String {
        var timeString = ""
        val mins = kotlin.math.floor((double / 60))
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