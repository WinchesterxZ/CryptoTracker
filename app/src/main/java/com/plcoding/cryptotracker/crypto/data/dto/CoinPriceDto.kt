package com.plcoding.cryptotracker.crypto.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinPriceDto(
    @SerialName("priceUsd")
    val price:Double,
    @SerialName("time")
    val dateTime:Long
)
