package ru.wanket.exchange_trading_assistant.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.wanket.exchange_trading_assistant.entity.data.FavoriteSettings
import ru.wanket.exchange_trading_assistant.entity.data.RateBaseInfo
import ru.wanket.exchange_trading_assistant.entity.data.RateType
import ru.wanket.exchange_trading_assistant.repository.FavoritesRepository
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class FavoriteSettingsViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {
    var rateBaseInfo = RateBaseInfo("", RateType.Crypto)
        set(value) {
            field = value

            reloadSettings()
        }

    var isEnabled by mutableStateOf(false)

    var upperBound by mutableStateOf("")

    var lowerBound by mutableStateOf("")

    var startTime: LocalDate by mutableStateOf(LocalDate.now())

    fun onSettingsChanged() = viewModelScope.launch {
        var (lower, upper) = listOf(lowerBound, upperBound)
            .map { it.ifEmpty { "0" } }
            .map { it.toDouble() }

        if (lower > upper) {
            lowerBound = upperBound

            lower = upper
        }

        favoritesRepository.updateFavorite(
            rateBaseInfo,
            FavoriteSettings(isEnabled, upper, lower, startTime)
        )
    }

    private fun reloadSettings() = viewModelScope.launch {
        favoritesRepository.getFavoriteSettings(rateBaseInfo).let { newSettings ->
            isEnabled = newSettings.isEnabled
            upperBound = newSettings.upperBound.run { if (this == .0) "" else toString() }
            lowerBound = newSettings.lowerBound.run { if (this == .0) "" else toString() }
            startTime = newSettings.startDate
        }
    }
}
