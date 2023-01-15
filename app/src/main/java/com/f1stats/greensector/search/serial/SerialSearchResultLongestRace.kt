package com.f1stats.greensector.search.serial

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SerialSearchResultLongestRace(
    @SerialName("circuit_name")
    val circuitName: String,
    @SerialName("race_distance")
    val raceDistance: Float,
)
