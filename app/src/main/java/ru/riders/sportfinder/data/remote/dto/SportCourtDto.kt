package ru.riders.sportfinder.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SportCourtsDto(
    @SerializedName("data")
    val sportCourts: ArrayList<SportCourt>
)

