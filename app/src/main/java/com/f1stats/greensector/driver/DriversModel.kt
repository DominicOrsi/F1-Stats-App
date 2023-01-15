package com.f1stats.greensector.driver

import android.util.Log
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val TAG = "DriversModel"

class DriversModel constructor (driverId: Int) {

    var firstName = "ERROR"
    var lastName = "Error"
    var number = driverId
    var dateOfBirth = "ERROR"
    var nationality = "ERROR"
    var cWins = driverId
    var cPodiums = driverId
    var cFastestLap = driverId
    var cGp = driverId
    var sWins = driverId
    var sPodiums = driverId
    var sFastestLap = driverId
    var sGp = driverId


    init {
        this.callDriverFunctions(driverId)
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

    private suspend fun getDriversInfo(driverId: Int): List<SerialDriverInfo> {
        val serializedResult: List<SerialDriverInfo> = try {
            val client = createConnection()
            val result = client.postgrest["driver"]
                .select {
                    eq("driver_id", driverId)
                }
            client.close()
            result.decodeList()
        } catch (e: RestException) {
            println("Unable to make call")
            emptyList()
        }
        return serializedResult
    }

    private suspend fun getDriversStats(driverId: Int): List<SerialDriverStats> {
        val serializedResult: List<SerialDriverStats> = try {
            val client = createConnection()
            val result = client.postgrest["driverstatistics"]
                .select {
                    eq("driver_id", driverId)
                }
            client.close()
            result.decodeList()
        } catch (e: RestException) {
            println("Unable to make call")
            emptyList()
        }
        return serializedResult
    }

    private fun callDriverFunctions(driverId: Int) {
        val driverInfoJob = CoroutineScope(Dispatchers.IO).launch{

            val driverInfoResult = getDriversInfo(driverId)
            try {
                number = driverInfoResult[0].number
                firstName = driverInfoResult[0].first_name
                lastName = driverInfoResult[0].last_name
                dateOfBirth = driverInfoResult[0].date_of_birth
                nationality = driverInfoResult[0].nationality
            } catch (e: java.lang.IndexOutOfBoundsException) {
                Log.e(TAG, "Driver Info unable to serialize")
            }

        }

        val driverStatsJob = CoroutineScope(Dispatchers.IO).launch {
            val driverStatsInfo = getDriversStats(driverId)
            try {
                cWins = driverStatsInfo[0].career_wins
                cPodiums = driverStatsInfo[0].career_podiums
                cFastestLap = driverStatsInfo[0].career_fastest_laps
                cGp = driverStatsInfo[0].career_races
                sWins = driverStatsInfo[0].season_wins
                sPodiums = driverStatsInfo[0].season_podiums
                sFastestLap = driverStatsInfo[0].season_fastest_laps
                sGp = driverStatsInfo[0].season_races
            } catch (e: java.lang.IndexOutOfBoundsException) {
                Log.e(TAG, "Driver Stats unable to serialize")
            }
        }

        // This is such a bad way to check if the threads are done,
        // should try and use a promise and await()/awaitAll()
        while(!driverInfoJob.status() && !driverStatsJob.status())
        {
            Thread.sleep(250)
        }
        Thread.sleep(250)
    }

    private fun Job.status(): Boolean = when {
        isCompleted -> true
        else -> false
    }

}