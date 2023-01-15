package com.f1stats.greensector.search.serial

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SerialSearchResultDriverBestFinishes(
    @SerialName("circuit_name")
    val circuitName: String,
    @SerialName("best_position")
    val position: Int
)
