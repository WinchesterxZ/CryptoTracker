package com.plcoding.cryptotracker

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.cryptotracker.core.presentation.util.ObserveAsEvent
import com.plcoding.cryptotracker.core.presentation.util.errorToString
import com.plcoding.cryptotracker.crypto.presentation.coin_details.CoinDetailsScreen
import com.plcoding.cryptotracker.crypto.presentation.coin_list.CoinListEvent
import com.plcoding.cryptotracker.crypto.presentation.coin_list.CoinListScreen
import com.plcoding.cryptotracker.crypto.presentation.coin_list.CoinListState
import com.plcoding.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import com.plcoding.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTrackerTheme {
               Scaffold(modifier = Modifier.fillMaxSize()) {  innerPadding ->
                   val viewModel = koinViewModel<CoinListViewModel>()
                   val state by viewModel.state.collectAsStateWithLifecycle()
                   ObserveAsEvent(event = viewModel.event) { event->
                       when(event){
                           is CoinListEvent.Error -> {
                               Toast.makeText(this, event.message.errorToString(this), Toast.LENGTH_LONG).show()
                           }
                       }

                   }
                   when{
                       state.selectedCoin != null -> {
                           CoinDetailsScreen(
                               state = state,
                               modifier = Modifier.padding(innerPadding)
                           )
                       }else -> {
                           CoinListScreen(
                               state = state,
                               onAction = viewModel::onAction,
                           )
                       }
                   }

               }
            }
        }
    }
}

