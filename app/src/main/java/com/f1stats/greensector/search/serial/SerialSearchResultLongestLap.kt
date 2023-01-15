package com.f1stats.greensector.search.serial

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SerialSearchResultLongestLap(
    @SerialName("circuit_name")
    val circuitName: String,
    @SerialName("lap_distance")
    val lapDistance: Float,
)
