package com.f1stats.greensector.search.serial

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SerialSearchResultCircuit(
    @SerialName("circuit_name")
    val circuitName: String,
    @SerialName("lap_time")
    var lapTime: Double,
    val rank: Int,
)
