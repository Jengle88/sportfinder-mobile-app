package ru.riders.sportfinder.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SignUpRequestBody(
    @SerializedName("login")
    @Expose
    val login: String,

    @SerializedName("password")
    @Expose
    val password: String
)