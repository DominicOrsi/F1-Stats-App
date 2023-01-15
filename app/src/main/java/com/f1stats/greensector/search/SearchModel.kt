package com.f1stats.greensector.search

import android.util.Log
import com.f1stats.greensector.search.serial.*
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

private const val TAG = "SearchModel"

class SearchModel constructor(searchId: Int) {
    var searchName = "ERROR"
    var oneName = "ERROR"
    var oneResult = "ERROR"
    var twoName = "ERROR"
    var twoResult = "ERROR"
    var threeName = "ERROR"
    var threeResult = "ERROR"
    var fourName = "ERROR"
    var fourResult = "ERROR"
    var fiveName = "ERROR"
    var fiveResult = "ERROR"
    private val searchNameArray = arrayOf("Fastest Lap Time at", "Longest Lap Distance",
        "Fastest Lap Time Overall", "Longest Race Distance", "Wins", "Podiums", "Races",
        "Fastest Laps", "Do Not Finishes (DNFs)", "Championship Standings at", "Average Points",
        "Average Finish", "Top Points", "Top Finishes")
    private var otherId = 1
    private var modelSearchId = searchId


    init {
        this.getSearchResult(modelSearchId)
    }

    private fun createConnection(): SupabaseClient {
        val client = createSupabaseClient(
            supabaseUrl = "https://dnknowgkpotzpxmcpvbe.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImRua25vd2drcG90enB4bWNwdmJlIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzEyNDQ2OTksImV4cCI6MTk4NjgyMDY5OX0.RakVDrvPjI8nQaQ_aLXDcRaPFodeLR7Q2vvf9lb5Np0"
        ) {
            install(Postgrest)
        }
        return client
    }

    // Circuit Button 1, Overall 1
    private suspend fun getFastestLapTimesForCircuit(circuitId: Int): List<SerialSearchResultCircuitFastestLaps> {
        val serializedResult: List<SerialSearchResultCircuitFastestLaps> = try {
            val client = createConnection()
            val result = client.postgrest.rpc("fastest_laps_at_circuit", mapOf("id" to circuitId))
            client.close()
            result.decodeList()
        } catch (e: RestException) {
            println("Unable to make call")
            emptyList()
        }
        return serializedResult
    }

    // Circuit Button 2, Overall 2
    private suspend fun getLongestLapDistance(): List<SerialSearchResultLongestLap> {
        val serializedResult: List<SerialSearchResultLongestLap> = try {
            val client = createConnection()
            val result = client.postgrest.rpc("longest_lap_distance")
            client.close()
            result.decodeList()
        } catch (e: RestException) {
            println("Unable to make call")
            emptyList()
        }
        return serializedResult
    }

    // Circuit Button 3, Overall 3
    private suspend fun getFastestLapTimeOverall(): List<SerialSearchResultCircuit> {
        val serializedResult: List<SerialSearchResultCircuit> = try {
            val client = createConnection()
            val result = client.postgrest.rpc("fastest_lap_time_overall")
            client.close()
            result.decodeList()
        } catch (e: RestException) {
            println("Unable to make call")
            emptyList()
        }
        return serializedResult
    }

    // Circuit Button 4, Overall 4
    private suspend fun getLongestRaceDistance(): List<SerialSearchResultLongestRace> {
        val serializedResult: List<SerialSearchResultLongestRace> = try {
            val client = createConnection()
            val result = client.postgrest.rpc("longest_race_distance")
            client.close()
            result.decodeList()
        } catch (e: RestException) {
            println("Unable to make call")
            emptyList()
        }
        return serializedResult
    }

    // Driver Button 1, Overall 5
    private suspend fun getMostDriverWins(): List<SerialSearchResultDriverWins> {
        val serializedResult: List<SerialSearchResultDriverWins> = try {
            val client = createConnection()
            val result = client.postgrest.rpc("driver_wins")
            client.close()
            result.decodeList()
        } catch (e: RestException) {
            println("Unable to make call")
            emptyList()
        }
        return serializedResult
    }

    // Driver Button 2, Overall 6
    private suspend fun getMostDriverPodiums(): List<SerialSearchResultDriverPodiums> {
        val serializedResult: List<SerialSearchResultDriverPodiums> = try {
            val client = createConnection()
            val result = client.postgrest.rpc("driver_podiums")
            client.close()
            result.decodeList()
        } catch (e: RestException) {
            println("Unable to make call")
            emptyList()
        }
        return serializedResult
    }

    // Driver Button 3, Overall 7
    private suspend fun getMostDriverRaces(): List<SerialSearchResultDriverRaces> {
        val serializedResult: List<SerialSearchResultDriverRaces> = try {
            val client = createConnection()
            val result = client.postgrest.rpc("driver_races")
            client.close()
            result.decodeList()
        } catch (e: RestException) {
            println("Unable to make call")
            emptyList()
        }
        return serializedResult
    }

    // Driver Button 4, Overall 8
    private suspend fun getMostDriverFastestLaps(): List<SerialSearchResultDriverFastestLaps> {
        val serializedResult: List<SerialSearchResultDriverFastestLaps> = try {
            val client = createConnection()
            val result = client.postgrest.rpc("driver_fastest_laps")
            client.close()
            result.decodeList()
        } catch (e: RestException) {
            println("Unable to make call")
            emptyList()
        }
        return serializedResult
    }

    // Driver Button 5, Overall 9
    private suspend fun getMostDriverDNFs(): List<SerailSearchResultDriverDNFs> {
        val serializedResult: List<SerailSearchResultDriverDNFs> = try {
            val client = createConnection()
            val result = client.postgrest.rpc("driver_dnfs")
            client.close()
            result.decodeList()
        } catch (e: RestException) {
            println("Unable to make call")
            emptyList()
        }
        return serializedResult
    }

    // Driver Button 6, Overall 10
    private suspend fun getDriverStandingsAt(circuitId: Int): List<SerialSearchResultDriverStandings> {
        val serializedResult: List<SerialSearchResultDriverStandings> = try {
            val client = createConnection()
            val result = client.postgrest.rpc("driver_standings_at", mapOf("id" to circuitId))
            client.close()
            result.decodeList()
        } catch (e: RestException) {
            println("Unable to make call")
            emptyList()
        }
        return serializedResult
    }

    // Driver Button 7, Overall 11
    private suspend fun getDriversAvgPoints(): List<SerialSearchResultDriverPoints> {
        val serializedResult: List<SerialSearchResultDriverPoints> = try {
            val client = createConnection()
            val result = client.postgrest.rpc("driver_all_avg_points")
            client.close()
            result.decodeList()
        } catch (e: RestException) {
            println("Unable to make call")
            emptyList()
        }
        return serializedResult
    }

    // Driver Button 7, Overall 12
    private suspend fun getDriversAvgFinish(): List<SerialSearchResultDriverFinish> {
        val serializedResult: List<SerialSearchResultDriverFinish> = try {
            val client = createConnection()
            val result = client.postgrest.rpc("driver_all_avg_finish")
            client.close()
            result.decodeList()
        } catch (e: RestException) {
            println("Unable to make call")
            emptyList()
        }
        return serializedResult
    }

    // Driver Narrowed Button 1, Overall 13
    private suspend fun getDriversTopPoints(driverId: Int): List<SerialSearchResultDriverBestPoints> {
        val serializedResult: List<SerialSearchResultDriverBestPoints> = try {
            val client = createConnection()
            val result = client.postgrest.rpc("driver_top_points", mapOf("id" to driverId))
            client.close()
            println(result)
            println(result.decodeList<SerialSearchResultDriverBestPoints>())
            result.decodeList()
        } catch (e: RestException) {
            println("Unable to make call")
            emptyList()
        }
        return serializedResult
    }

    // Driver Narrowed Button 2, Overall 14
    private suspend fun getDriversTopFinishes(driverId: Int): List<SerialSearchResultDriverBestFinishes> {
        val serializedResult: List<SerialSearchResultDriverBestFinishes> = try {
            val client = createConnection()
            val result = client.postgrest.rpc("driver_top_finish", mapOf("id" to driverId))
            println(client)
            client.close()
            println(result)
            result.decodeList()
        } catch (e: RestException) {
            println("Unable to make call")
            emptyList()
        }
        return serializedResult
    }

    private fun getSearchResult(searchId: Int) {
        val searchJob = CoroutineScope(Dispatchers.IO).launch {
            if (modelSearchId > 20) {
                val temp = floor(searchId.toDouble()/100).toInt()
                otherId = modelSearchId - (temp * 100)
                modelSearchId = temp
                println("otherId: $otherId")
                println("modelSearchId: $modelSearchId")
            }
            when (modelSearchId) {
                1 -> functionCallFastestLapTimesForCircuit(modelSearchId, otherId)
                2 -> functionCallLongestLapTime(modelSearchId)
                3 -> functionCallLapTimeOverall(modelSearchId)
                4 -> functionCallLongestRaceDistance(modelSearchId)
                5 -> functionCallMostDriverWins(modelSearchId)
                6 -> functionCallMostDriverPodiums(modelSearchId)
                7 -> functionCallMostDriverRaces(modelSearchId)
                8 -> functionCallMostDriverFastestLaps(modelSearchId)
                9 -> functionCallMostDriverDNFs(modelSearchId)
                10 -> functionCallDriverStandings(modelSearchId, otherId)
                11 -> functionCallDriverAvgPoints(modelSearchId, otherId)
                12 -> functionCallDriverAvgFinish(modelSearchId, otherId)
                13 -> functionCallDriverTopPoints(modelSearchId, otherId)
                14 -> functionCallDriverTopFinishes(modelSearchId, otherId)
                else -> functionCallDriverStandings(modelSearchId, otherId)
            }
        }

        // This is such a bad way to check if the threads are done,
        // should try and use a promise and await()/awaitAll()
        while(!searchJob.status())
        {
            Thread.sleep(250)
        }
        Thread.sleep(250)
    }

    private fun Job.status(): Boolean = when {
        isCompleted -> true
        else -> false
    }
    // Complete Function Call to create and init FastestLapTimesForCircuit
    private suspend fun functionCallFastestLapTimesForCircuit(searchId: Int, circuitId: Int) {
        val searchResult = getFastestLapTimesForCircuit(circuitId)
        try{
            searchName = searchNameArray[searchId - 1] + " " + searchResult[0].circuitName
            oneName = searchResult[0].firstName + " " + searchResult[0].lastName.uppercase()
            oneResult = lapTimeToTimeFormat(searchResult[0].lapTime)
            twoName = searchResult[1].firstName + " " + searchResult[1].lastName.uppercase()
            twoResult = lapTimeToTimeFormat(searchResult[1].lapTime)
            threeName = searchResult[2].firstName + " " + searchResult[2].lastName.uppercase()
            threeResult = lapTimeToTimeFormat(searchResult[2].lapTime)
            fourName= searchResult[3].firstName + " " + searchResult[3].lastName.uppercase()
            fourResult = lapTimeToTimeFormat(searchResult[3].lapTime)
            fiveName= searchResult[4].firstName + " " + searchResult[4].lastName.uppercase()
            fiveResult = lapTimeToTimeFormat(searchResult[4].lapTime)
        } catch (e: java.lang.IndexOutOfBoundsException) {
            Log.e(TAG, "Result Info unable to serialize")
        }
    }

    // Complete Function Call to create and init LongestLapDistance
    private suspend fun functionCallLongestLapTime(searchId: Int) {
        val searchResult = getLongestLapDistance()
        try {
            searchName = searchNameArray[searchId - 1]
            oneName = searchResult[0].circuitName
            oneResult = searchResult[0].lapDistance.toString() + " km"
            twoName = searchResult[1].circuitName
            twoResult = searchResult[1].lapDistance.toString() + " km"
            threeName = searchResult[2].circuitName
            threeResult = searchResult[2].lapDistance.toString() + " km"
            fourName= searchResult[3].circuitName
            fourResult = searchResult[3].lapDistance.toString() + " km"
            fiveName= searchResult[4].circuitName
            fiveResult = searchResult[4].lapDistance.toString() + " km"
        } catch (e: java.lang.IndexOutOfBoundsException) {
            Log.e(TAG, "Result Info unable to serialize")
        }

    }

    // Complete Function Call to create and init FastestLapTimeOverall
    private suspend fun functionCallLapTimeOverall(searchId: Int) {
        val searchResult = getFastestLapTimeOverall()
        try {
            searchName = searchNameArray[searchId - 1]
            oneName = searchResult[0].circuitName
            oneResult = lapTimeToTimeFormat(searchResult[0].lapTime)
            twoName = searchResult[1].circuitName
            twoResult = lapTimeToTimeFormat(searchResult[1].lapTime)
            threeName = searchResult[2].circuitName
            threeResult = lapTimeToTimeFormat(searchResult[2].lapTime)
            fourName= searchResult[3].circuitName
            fourResult = lapTimeToTimeFormat(searchResult[3].lapTime)
            fiveName= searchResult[4].circuitName
            fiveResult = lapTimeToTimeFormat(searchResult[4].lapTime)
        } catch (e: java.lang.IndexOutOfBoundsException) {
            Log.e(TAG, "Result Info unable to serialize")
        }
    }

    // Complete Function Call to create and init LongestRaceDistance
    private suspend fun functionCallLongestRaceDistance(searchId: Int) {
        val searchResult = getLongestRaceDistance()
        try {
            searchName = searchNameArray[searchId - 1]
            oneName = searchResult[0].circuitName
            oneResult = searchResult[0].raceDistance.toString() + " km"
            twoName = searchResult[1].circuitName
            twoResult = searchResult[1].raceDistance.toString() + " km"
            threeName = searchResult[2].circuitName
            threeResult = searchResult[2].raceDistance.toString() + " km"
            fourName= searchResult[3].circuitName
            fourResult = searchResult[3].raceDistance.toString() + " km"
            fiveName= searchResult[4].circuitName
            fiveResult = searchResult[4].raceDistance.toString() + " km"
        } catch (e: java.lang.IndexOutOfBoundsException) {
            Log.e(TAG, "Result Info unable to serialize")
        }
    }

    // Complete Function Call to create and init MostRaceWins
    private suspend fun functionCallMostDriverWins(searchId: Int) {
        val searchResult = getMostDriverWins()
        try {
            searchName = searchNameArray[searchId - 1]
            oneName = searchResult[0].firstName + " " + searchResult[0].lastName.uppercase()
            oneResult = searchResult[0].wins.toString()
            twoName = searchResult[1].firstName + " " + searchResult[1].lastName.uppercase()
            twoResult = searchResult[1].wins.toString()
            threeName = searchResult[2].firstName + " " + searchResult[2].lastName.uppercase()
            threeResult = searchResult[2].wins.toString()
            fourName= searchResult[3].firstName + " " + searchResult[3].lastName.uppercase()
            fourResult = searchResult[3].wins.toString()
            fiveName= searchResult[4].firstName + " " + searchResult[4].lastName.uppercase()
            fiveResult = searchResult[4].wins.toString()
        } catch (e: java.lang.IndexOutOfBoundsException) {
            Log.e(TAG, "Result Info unable to serialize")
        }
    }

    // Complete Function Call to create and init MostDriverPodiums
    private suspend fun functionCallMostDriverPodiums(searchId: Int) {
        val searchResult = getMostDriverPodiums()
        try {
            searchName = searchNameArray[searchId - 1]
            oneName = searchResult[0].firstName + " " + searchResult[0].lastName.uppercase()
            oneResult = searchResult[0].podiums.toString()
            twoName = searchResult[1].firstName + " " + searchResult[1].lastName.uppercase()
            twoResult = searchResult[1].podiums.toString()
            threeName = searchResult[2].firstName + " " + searchResult[2].lastName.uppercase()
            threeResult = searchResult[2].podiums.toString()
            fourName= searchResult[3].firstName + " " + searchResult[3].lastName.uppercase()
            fourResult = searchResult[3].podiums.toString()
            fiveName= searchResult[4].firstName + " " + searchResult[4].lastName.uppercase()
            fiveResult = searchResult[4].podiums.toString()
        } catch (e: java.lang.IndexOutOfBoundsException) {
            Log.e(TAG, "Result Info unable to serialize")
        }
    }

    // Complete Function Call to create and init MostDriverRaces
    private suspend fun functionCallMostDriverRaces(searchId: Int){
        val searchResult = getMostDriverRaces()
        try {
            searchName = searchNameArray[searchId - 1]
            oneName = searchResult[0].firstName + " " + searchResult[0].lastName.uppercase()
            oneResult = searchResult[0].races.toString()
            twoName = searchResult[1].firstName + " " + searchResult[1].lastName.uppercase()
            twoResult = searchResult[1].races.toString()
            threeName = searchResult[2].firstName + " " + searchResult[2].lastName.uppercase()
            threeResult = searchResult[2].races.toString()
            fourName= searchResult[3].firstName + " " + searchResult[3].lastName.uppercase()
            fourResult = searchResult[3].races.toString()
            fiveName= searchResult[4].firstName + " " + searchResult[4].lastName.uppercase()
            fiveResult = searchResult[4].races.toString()
        } catch (e: java.lang.IndexOutOfBoundsException) {
            Log.e(TAG, "Result Info unable to serialize")
        }
    }

    // Complete Function Call to create and init MostDriverFastestLaps
    private suspend fun functionCallMostDriverFastestLaps(searchId: Int) {
        val searchResult = getMostDriverFastestLaps()
        try {
            searchName = searchNameArray[searchId - 1]
            oneName = searchResult[0].firstName + " " + searchResult[0].lastName.uppercase()
            oneResult = searchResult[0].fastestLaps.toString()
            twoName = searchResult[1].firstName + " " + searchResult[1].lastName.uppercase()
            twoResult = searchResult[1].fastestLaps.toString()
            threeName = searchResult[2].firstName + " " + searchResult[2].lastName.uppercase()
            threeResult = searchResult[2].fastestLaps.toString()
            fourName= searchResult[3].firstName + " " + searchResult[3].lastName.uppercase()
            fourResult = searchResult[3].fastestLaps.toString()
            fiveName= searchResult[4].firstName + " " + searchResult[4].lastName.uppercase()
            fiveResult = searchResult[4].fastestLaps.toString()
        } catch (e: java.lang.IndexOutOfBoundsException) {
            Log.e(TAG, "Result Info unable to serialize")
        }
    }

    // Complete Function Call to create and init MostDriverDNFs
    private suspend fun functionCallMostDriverDNFs(searchId: Int) {
        val searchResult = getMostDriverDNFs()
        try {
            searchName = searchNameArray[searchId - 1]
            oneName = searchResult[0].firstName + " " + searchResult[0].lastName.uppercase()
            oneResult = searchResult[0].dnfs.toString()
            twoName = searchResult[1].firstName + " " + searchResult[1].lastName.uppercase()
            twoResult = searchResult[1].dnfs.toString()
            threeName = searchResult[2].firstName + " " + searchResult[2].lastName.uppercase()
            threeResult = searchResult[2].dnfs.toString()
            fourName= searchResult[3].firstName + " " + searchResult[3].lastName.uppercase()
            fourResult = searchResult[3].dnfs.toString()
            fiveName= searchResult[4].firstName + " " + searchResult[4].lastName.uppercase()
            fiveResult = searchResult[4].dnfs.toString()
        } catch (e: java.lang.IndexOutOfBoundsException) {
            Log.e(TAG, "Result Info unable to serialize")
        }
    }

    // Complete Function Call to create and init DriverStandingsAt
    private suspend fun functionCallDriverStandings(searchId: Int, circuitId: Int) {
        val searchResult = getDriverStandingsAt(circuitId)
        try {
            searchName = searchNameArray[searchId - 1] + " " + searchResult[0].circuitName
            oneName = searchResult[0].firstName + " " + searchResult[0].lastName.uppercase()
            oneResult = searchResult[0].points.toString()
            twoName = searchResult[1].firstName + " " + searchResult[1].lastName.uppercase()
            twoResult = searchResult[1].points.toString()
            threeName = searchResult[2].firstName + " " + searchResult[2].lastName.uppercase()
            threeResult = searchResult[2].points.toString()
            fourName= searchResult[3].firstName + " " + searchResult[3].lastName.uppercase()
            fourResult = searchResult[3].points.toString()
            fiveName= searchResult[4].firstName + " " + searchResult[4].lastName.uppercase()
            fiveResult = searchResult[4].points.toString()
        } catch (e: java.lang.IndexOutOfBoundsException) {
            Log.e(TAG, "Result Info unable to serialize")
        }
    }

    // Complete Function Call to create and init DriverAvgPoints
    private suspend fun functionCallDriverAvgPoints(searchId: Int, ascOrDesc: Int) {
        val searchResult = getDriversAvgPoints()
        try {
            if (ascOrDesc == 0) {
                searchName = "Highest "+ searchNameArray[searchId - 1]
                oneName = searchResult[0].firstName + " " + searchResult[0].lastName.uppercase()
                oneResult = String.format("%.2f", searchResult[0].avgPoints) + " pts"
                twoName = searchResult[1].firstName + " " + searchResult[1].lastName.uppercase()
                twoResult = String.format("%.2f", searchResult[1].avgPoints) + " pts"
                threeName = searchResult[2].firstName + " " + searchResult[2].lastName.uppercase()
                threeResult = String.format("%.2f", searchResult[2].avgPoints) + " pts"
                fourName= searchResult[3].firstName + " " + searchResult[3].lastName.uppercase()
                fourResult = String.format("%.2f", searchResult[3].avgPoints) + " pts"
                fiveName= searchResult[4].firstName + " " + searchResult[4].lastName.uppercase()
                fiveResult = String.format("%.2f", searchResult[4].avgPoints) + " pts"
            } else {
                val resultLength = searchResult.size
                searchName = "Lowest "+ searchNameArray[searchId - 1]
                oneName = searchResult[resultLength - 1].firstName + " " + searchResult[resultLength - 1].lastName.uppercase()
                oneResult = String.format("%.2f", searchResult[resultLength - 1].avgPoints) + " pts"
                twoName = searchResult[resultLength - 2].firstName + " " + searchResult[resultLength - 2].lastName.uppercase()
                twoResult = String.format("%.2f", searchResult[resultLength - 2].avgPoints) + " pts"
                threeName = searchResult[resultLength - 3].firstName + " " + searchResult[resultLength - 3].lastName.uppercase()
                threeResult = String.format("%.2f", searchResult[resultLength - 3].avgPoints) + " pts"
                fourName= searchResult[resultLength - 4].firstName + " " + searchResult[resultLength - 4].lastName.uppercase()
                fourResult = String.format("%.2f", searchResult[resultLength - 4].avgPoints) + " pts"
                fiveName= searchResult[resultLength - 5].firstName + " " + searchResult[resultLength - 5].lastName.uppercase()
                fiveResult = String.format("%.2f", searchResult[resultLength - 5].avgPoints) + " pts"
            }
        } catch (e: java.lang.IndexOutOfBoundsException) {
            Log.e(TAG, "Result Info unable to serialize")
        }
    }

    private suspend fun functionCallDriverAvgFinish(searchId: Int, ascOrDesc: Int) {
        val searchResult = getDriversAvgFinish()
        try {
            if (ascOrDesc == 0) {
                searchName = "Highest "+ searchNameArray[searchId - 1]
                oneName = searchResult[0].firstName + " " + searchResult[0].lastName.uppercase()
                oneResult = String.format("%.2f", searchResult[0].avgPosition)
                twoName = searchResult[1].firstName + " " + searchResult[1].lastName.uppercase()
                twoResult = String.format("%.2f", searchResult[1].avgPosition)
                threeName = searchResult[2].firstName + " " + searchResult[2].lastName.uppercase()
                threeResult = String.format("%.2f", searchResult[2].avgPosition)
                fourName= searchResult[3].firstName + " " + searchResult[3].lastName.uppercase()
                fourResult = String.format("%.2f", searchResult[3].avgPosition)
                fiveName= searchResult[4].firstName + " " + searchResult[4].lastName.uppercase()
                fiveResult = String.format("%.2f", searchResult[4].avgPosition)
            } else {
                val resultLength = searchResult.size
                searchName = "Lowest "+ searchNameArray[searchId - 1]
                oneName = searchResult[resultLength - 1].firstName + " " + searchResult[resultLength - 1].lastName.uppercase()
                oneResult = String.format("%.2f", searchResult[resultLength - 1].avgPosition)
                twoName = searchResult[resultLength - 2].firstName + " " + searchResult[resultLength - 2].lastName.uppercase()
                twoResult = String.format("%.2f", searchResult[resultLength - 2].avgPosition)
                threeName = searchResult[resultLength - 3].firstName + " " + searchResult[resultLength - 3].lastName.uppercase()
                threeResult = String.format("%.2f", searchResult[resultLength - 3].avgPosition)
                fourName= searchResult[resultLength - 4].firstName + " " + searchResult[resultLength - 4].lastName.uppercase()
                fourResult = String.format("%.2f", searchResult[resultLength - 4].avgPosition)
                fiveName= searchResult[resultLength - 5].firstName + " " + searchResult[resultLength - 5].lastName.uppercase()
                fiveResult = String.format("%.2f", searchResult[resultLength - 5].avgPosition)
            }
        } catch (e: java.lang.IndexOutOfBoundsException) {
            Log.e(TAG, "Result Info unable to serialize")
        }
    }

    private suspend fun functionCallDriverTopPoints(searchId: Int, driverId: Int) {
        val searchResult = getDriversTopPoints(driverId)
        try {
            searchName = searchNameArray[searchId - 1]
            oneName = searchResult[0].circuitName
            oneResult = searchResult[0].points.toString()
            twoName = searchResult[1].circuitName
            twoResult = searchResult[1].points.toString()
            threeName = searchResult[2].circuitName
            threeResult = searchResult[2].points.toString()
            fourName= searchResult[3].circuitName
            fourResult = searchResult[3].points.toString()
            fiveName= searchResult[4].circuitName
            fiveResult = searchResult[4].points.toString()
        } catch (e: java.lang.IndexOutOfBoundsException) {
            Log.e(TAG, "Result Info unable to serialize")
        }
    }

    private suspend fun functionCallDriverTopFinishes(searchId: Int, driverId: Int){
        val searchResult = getDriversTopFinishes(driverId)
        try {
            searchName = searchNameArray[searchId - 1]
            oneName = searchResult[0].circuitName
            oneResult = searchResult[0].position.toString()
            twoName = searchResult[1].circuitName
            twoResult = searchResult[1].position.toString()
            threeName = searchResult[2].circuitName
            threeResult = searchResult[2].position.toString()
            fourName= searchResult[3].circuitName
            fourResult = searchResult[3].position.toString()
            fiveName= searchResult[4].circuitName
            fiveResult = searchResult[4].position.toString()
        } catch (e: java.lang.IndexOutOfBoundsException) {
            Log.e(TAG, "Result Info unable to serialize")
        }
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