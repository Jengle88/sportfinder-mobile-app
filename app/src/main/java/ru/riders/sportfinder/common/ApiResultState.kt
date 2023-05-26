package ru.riders.sportfinder.common

sealed class ApiResultState<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : ApiResultState<T>(data)
    class Error<T>(message: String, data: T? = null) : ApiResultState<T>(data, message)
    class Loading<T>(data: T? = null) : ApiResultState<T>(data)
}