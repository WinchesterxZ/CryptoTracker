package com.plcoding.cryptotracker.crypto.domain.mapper

import androidx.collection.intSetOf
import com.plcoding.cryptotracker.crypto.data.dto.CoinDto
import com.plcoding.cryptotracker.crypto.data.dto.CoinPriceDto
import com.plcoding.cryptotracker.crypto.domain.Coin
import com.plcoding.cryptotracker.crypto.domain.CoinPrice
import java.time.Instant
import java.time.ZonedDateTime

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr
    )
}
fun CoinPriceDto.toCoinPrice():CoinPrice{
    return CoinPrice(
        price = price,
        dateTime = Instant.ofEpochMilli(dateTime).atZone(ZonedDateTime.now().zone)
    )

}