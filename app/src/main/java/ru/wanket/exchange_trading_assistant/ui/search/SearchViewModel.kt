package ru.wanket.exchange_trading_assistant.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.wanket.exchange_trading_assistant.entity.RateBaseInfo

class SearchViewModel : ViewModel() {
    var foundedList = mutableStateListOf<RateBaseInfo>()
        private set

    var searchField by mutableStateOf("")

    fun onRateClicked(rateBaseInfo: RateBaseInfo) {

    }

    fun onSearchFieldChanged(search: String) {

    }
}
