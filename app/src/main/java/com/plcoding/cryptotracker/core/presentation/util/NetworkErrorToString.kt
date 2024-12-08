package com.plcoding.cryptotracker.core.presentation.util

import android.content.Context
import com.plcoding.cryptotracker.R
import com.plcoding.cryptotracker.core.domain.util.NetworkError

fun NetworkError.errorToString(context: Context):String{
    val resId = when(this){
        NetworkError.REQUEST_TIMEOUT-> R.string.request_timeout
        NetworkError.SERVER_ERROR-> R.string.server_error
        NetworkError.TOO_MANY_REQUESTS -> R.string.too_many_requests
        NetworkError.NO_INTERNET -> R.string.no_internet
        NetworkError.SERIALIZATION_ERROR -> R.string.serialization_error
        NetworkError.UNKNOWN_ERROR -> R.string.unknown_error

    }
    return context.getString(resId)
}