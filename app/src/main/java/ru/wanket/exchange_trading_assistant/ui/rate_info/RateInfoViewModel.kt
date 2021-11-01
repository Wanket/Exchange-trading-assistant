package ru.wanket.exchange_trading_assistant.ui.rate_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.wanket.exchange_trading_assistant.entity.data.Crypto
import ru.wanket.exchange_trading_assistant.entity.data.RateBaseInfo
import ru.wanket.exchange_trading_assistant.entity.data.RateFullInfo
import ru.wanket.exchange_trading_assistant.entity.data.RateType
import ru.wanket.exchange_trading_assistant.repository.FavoritesRepository
import ru.wanket.exchange_trading_assistant.repository.RateRepository
import java.time.LocalDate
import javax.inject.Inject

typealias History = List<Pair<LocalDate, Double>>

@HiltViewModel
class RateInfoViewModel @Inject constructor(
    private val rateRepository: RateRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {
    var rate by mutableStateOf<RateFullInfo>(Crypto("", ""))
        private set

    var history by mutableStateOf<History>(listOf())
        private set

    var prediction by mutableStateOf<History>(listOf())
        private set

    var rateBaseInfo = RateBaseInfo("", RateType.Crypto)
        set(value) {
            field = value

            reloadRate()
        }

    var isFavorite by mutableStateOf(false)

    fun onFavoriteChanged() = viewModelScope.launch {
        if (isFavorite) {
            favoritesRepository.addFavorite(rateBaseInfo)
        } else {
            favoritesRepository.deleteFavorite(rateBaseInfo)
        }
    }

    private fun reloadRate() = viewModelScope.launch {
        launch { isFavorite = favoritesRepository.isFavorite(rateBaseInfo) }

        if (rateBaseInfo.type == RateType.Crypto) {
            reloadRateTemplate(
                { rateRepository.getCryptoInfo(it)!! },
                { rateRepository.getCryptoHistory(it) }
            )
        } else {
            reloadRateTemplate(
                { rateRepository.getFullCompanyInfo(it) },
                { rateRepository.getCompanyHistory(it) }
            )
        }
    }

    private inline fun reloadRateTemplate(
        crossinline infoGetter: suspend (String) -> RateFullInfo,
        crossinline historyGetter: suspend (String) -> History
    ) = viewModelScope.launch {
        launch {
            history = historyGetter(rateBaseInfo.codeName).take(15)

            reloadPrediction()
        }

        rate = infoGetter(rateBaseInfo.codeName)
    }

    private fun reloadPrediction() {

    }
}
