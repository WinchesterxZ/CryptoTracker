package com.plcoding.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptotracker.core.domain.util.onError
import com.plcoding.cryptotracker.core.domain.util.onSuccess
import com.plcoding.cryptotracker.crypto.domain.CoinDataSource
import com.plcoding.cryptotracker.crypto.presentation.model.CoinUi
import com.plcoding.cryptotracker.crypto.presentation.model.toCoinUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class CoinListViewModel(
    private val coinDataSource: CoinDataSource
):ViewModel(){

    private val _state = MutableStateFlow(CoinListState())
    val state = _state
        .onStart {
            loadCoins()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000)
            , CoinListState()
        )

    private val _event = Channel<CoinListEvent>()
    val event = _event.receiveAsFlow()

    fun onAction(action: CoinListAction){
        when(action){
            is CoinListAction.OnCoinClick -> {
                selectCoin(action.coinUi)
            }
            is CoinListAction.OnRefresh -> loadCoins()
        }
    }

    private fun selectCoin(coinUi: CoinUi){
        _state.update {
            it.copy(selectedCoin = coinUi)
        }
        viewModelScope.launch {
            coinDataSource.getCoinHistory(
                coinId = coinUi.id,
                start = ZonedDateTime.now().minusDays(5),
                end = ZonedDateTime.now()
            ).onSuccess {history->
                println(history)
            }.onError {error->
                _state.update { it.copy(isLoading = false) }
                _event.send(CoinListEvent.Error(error))
            }
        }
    }


    private fun loadCoins(){
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(isLoading = true)
            }
            coinDataSource.getCoins()
                .onSuccess { coins->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            coins = coins.map { coin->
                                coin.toCoinUi()
                            }
                        )
                    }

                }
                .onError {error->
                    _state.update { it.copy(isLoading = false) }
                    _event.send(CoinListEvent.Error(error))
                }
        }
    }
}

