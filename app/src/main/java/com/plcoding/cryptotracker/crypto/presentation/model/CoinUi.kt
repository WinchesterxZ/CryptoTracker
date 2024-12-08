package com.plcoding.cryptotracker.crypto.presentation.model

import android.icu.text.NumberFormat
import androidx.annotation.DrawableRes
import com.plcoding.cryptotracker.crypto.domain.Coin
import com.plcoding.cryptotracker.core.presentation.util.getDrawableIdForCoin
import java.util.Locale

data class CoinUi(
    val id:String,
    val rank:Int,
    val name:String,
    val symbol:String,
    val marketCapUsd:DisplayableNumber,
    val priceUsd:DisplayableNumber,
    val changePercent24Hr:DisplayableNumber,
    @DrawableRes val iconRes:Int,
)

data class DisplayableNumber(
    val value:Double,
    val unit:String,

)

fun Coin.toCoinUi():CoinUi{
    return CoinUi(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd.toDisplayableNumber(),
        priceUsd = priceUsd.toDisplayableNumber(),
        changePercent24Hr = changePercent24Hr.toDisplayableNumber(),
        iconRes = getDrawableIdForCoin(symbol),
    )
}

fun Double.toDisplayableNumber():DisplayableNumber{
    val unit = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }
    return DisplayableNumber(
        value = this,
        unit = unit.format(this))
}