package ru.riders.sportfinder.common.utils

object NetworkUtils {
    fun asBearerHeader(data: String): String = "Bearer $data"
}