package com.arditakrasniqi.mymobilelife.utils

/** This class will help us to handle the response from API
 * Here we have three states : [Success, Loading, Error]
 * */
sealed class DataState<T>(
    val data: T? = null,
    val message: String? = null,
    var error: Errors? = null
) {
    class Success<T>(data: T) : DataState<T>(data)
    class Loading<T>(data: T? = null) : DataState<T>(data)
    class Error<T>(error: Errors?, message: String, data: T? = null) :
        DataState<T>(data, message, error)

}

enum class Errors {
    NETWORK, SERVER, TIMEOUT, UNKNOWN
}