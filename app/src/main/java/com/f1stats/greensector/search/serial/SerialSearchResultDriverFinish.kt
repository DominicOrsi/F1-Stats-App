package com.f1stats.greensector.search.serial

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SerialSearchResultDriverFinish(
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("avg_position")
    val avgPosition: Float
)
