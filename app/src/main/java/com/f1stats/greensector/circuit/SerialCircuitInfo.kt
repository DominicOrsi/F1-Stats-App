package com.f1stats.greensector.circuit

import kotlinx.serialization.Serializable

@Serializable
data class SerialCircuitInfo (
    val circuit_id: Int,
    val name: String,
    val sectors: Int,
    val turns: Int,
    val left_turns: Int,
    val right_turns: Int,
    val lap_distance: Double,
    val race_distance: Double,
    val laps: Int,
    val first_race: Int,
)