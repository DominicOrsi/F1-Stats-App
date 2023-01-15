package com.f1stats.greensector.search.serial

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SerialSearchResultDriverBestPoints(
    @SerialName("circuit_name")
    val circuitName: String,
    val points: Int
)