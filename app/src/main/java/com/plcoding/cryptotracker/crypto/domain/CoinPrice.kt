package com.plcoding.cryptotracker.crypto.domain

import java.time.ZonedDateTime

data class CoinPrice(
    val price:Double,
    val dateTime: ZonedDateTime
)
