package ru.wanket.exchange_trading_assistant.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workday.insights.timeseries.arima.Arima
import com.workday.insights.timeseries.arima.struct.ArimaParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.wanket.exchange_trading_assistant.entity.data.Crypto
import ru.wanket.exchange_trading_assistant.entity.data.RateBaseInfo
import ru.wanket.exchange_trading_assistant.entity.data.RateFullInfo
import ru.wanket.exchange_trading_assistant.entity.data.RateType
import ru.wanket.exchange_trading_assistant.repository.FavoritesRepository
import ru.wanket.exchange_trading_assistant.repository.RateRepository
import java.time.LocalDate
import java.util.*
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
            val fullHistory = historyGetter(rateBaseInfo.codeName)

            history = fullHistory.take(15).reversed().run {
                val (date, value) = last()

                val currentDate = LocalDate.now()

                if (date != currentDate) {
                    this + listOf(currentDate to value)
                } else {
                    this
                }
            }

            reloadPrediction(fullHistory.reversed())
        }

        rate = infoGetter(rateBaseInfo.codeName)
    }

    private fun reloadPrediction(fullHistory: History) = viewModelScope.launch {
        val result = Arima.forecast_arima(
            fullHistory.map { it.second }.toDoubleArray(),
            15,
            ArimaParams(10, 0, 20, 1, 0, 1, 12)
        )

        LocalDate.now().apply {
            prediction = listOf(history.last()) + result.forecast.mapIndexed { i, it ->
                plusDays((i + 1).toLong()) to it
            }
        }
    }
}
