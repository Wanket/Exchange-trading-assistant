package ru.wanket.exchange_trading_assistant.ui.favorites

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import ru.wanket.exchange_trading_assistant.entity.RateBaseInfo

class FavoritesViewModel : ViewModel() {
    val favorites = mutableStateListOf<RateBaseInfo>()

    fun onEditClicked(rateBaseInfo: RateBaseInfo) {

    }

    fun onRateClicked(rateBaseInfo: RateBaseInfo) {

    }

    fun onDeleteClicked(rateBaseInfo: RateBaseInfo) {

    }
}
