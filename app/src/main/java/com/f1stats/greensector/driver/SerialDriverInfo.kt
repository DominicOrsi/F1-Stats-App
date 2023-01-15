package com.f1stats.greensector.driver

import kotlinx.serialization.Serializable

@Serializable
data class SerialDriverInfo(
    val driver_id: Int,
    val number: Int,
    val first_name: String,
    val last_name: String,
    val date_of_birth: String,
    val nationality: String,
)