package ru.wanket.exchange_trading_assistant.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import ru.wanket.exchange_trading_assistant.entity.data.RateBaseInfo
import ru.wanket.exchange_trading_assistant.repository.SearchRepository
import ru.wanket.exchange_trading_assistant.ui.Navigator
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val navigator: Navigator
) : ViewModel() {
    var searchField by mutableStateOf("")

    var foundedList = mutableStateListOf<RateBaseInfo>()

    private val searchChannel = Channel<String>(Channel.CONFLATED)

    init {
        updateInfosLoop()
    }

    fun onSearchFieldChanged(search: String) {
        if (search.isNotEmpty()) {
            viewModelScope.launch { searchChannel.send(search) }
        } else {
            foundedList.clear()
        }
    }

    private fun updateInfosLoop() = viewModelScope.launch {
        while (true) {
            val infos = searchRepository.getRateBaseInfos(searchChannel.receive())

            foundedList.apply {
                clear()
                addAll(infos)
            }
        }
    }

    fun onRateClicked(rateBaseInfo: RateBaseInfo) = navigator.navigateRateInfo(rateBaseInfo)
}
