package com.f1stats.greensector.driver

import kotlinx.serialization.Serializable

@Serializable
data class SerialDriverStats(
    val driver_id: Int,
    val career_wins: Int,
    val career_podiums: Int,
    val career_fastest_laps: Int,
    val career_races: Int,
    val season_wins: Int,
    val season_podiums: Int,
    val season_fastest_laps: Int,
    val season_races: Int,
)