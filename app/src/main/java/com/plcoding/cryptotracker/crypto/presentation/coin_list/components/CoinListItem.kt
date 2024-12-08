package com.plcoding.cryptotracker.crypto.presentation.coin_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.cryptotracker.crypto.domain.Coin
import com.plcoding.cryptotracker.crypto.presentation.model.CoinUi
import com.plcoding.cryptotracker.crypto.presentation.model.toCoinUi
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme

@Composable
fun CoinListItem(
    coin: CoinUi,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ListItem(
        headlineContent = {
            Text(
                text = coin.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        modifier =
        modifier
            .clickable(onClick = onItemClick),
        supportingContent = {
            Text(
                text = coin.symbol,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light
            )
        },
        leadingContent = {
            Icon(
                imageVector = ImageVector.vectorResource(id = coin.iconRes),
                contentDescription = coin.name,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(85.dp)
            )
        },
        colors = ListItemDefaults.colors(
            containerColor =  MaterialTheme.colorScheme.background
        ),
        trailingContent = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "$ ${coin.priceUsd.unit}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(8.dp))
                PriceChange(
                    change = coin.changePercent24Hr
                )

            }
        },
    )
}




@PreviewLightDark
@Composable
private fun CoinListItemPreview() {
    CryptoTrackerTheme {
        CoinListItem(coin = previewCoin, onItemClick = {})

    }

}

   internal val previewCoin = Coin(
       id = "btc-bitcoin",
       rank = 1,
       name = "Bitcoin",
       symbol = "BTC",
       marketCapUsd =1242739588896.75,
       priceUsd = 62828.15,
       changePercent24Hr = -0.1,
   ).toCoinUi()
