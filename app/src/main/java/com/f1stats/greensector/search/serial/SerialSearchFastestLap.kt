package com.f1stats.greensector.search.serial

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SerialSearchFastestLap(
    @SerialName("driver_id")
    val driverId: Int,
    @SerialName("race_id")
    val raceId: Int,
    val lap: Int,
    @SerialName("lap_time")
    val lapTime: Float
)
