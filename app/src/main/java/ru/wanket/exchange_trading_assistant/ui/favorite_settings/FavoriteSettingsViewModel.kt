package ru.wanket.exchange_trading_assistant.ui.favorite_settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.wanket.exchange_trading_assistant.entity.RateBaseInfo
import ru.wanket.exchange_trading_assistant.entity.RateType
import java.time.LocalDate

class FavoriteSettingsViewModel : ViewModel() {
    val rate by mutableStateOf<RateBaseInfo>(RateBaseInfo("", RateType.Company))

    var isEnabled by mutableStateOf(false)

    var upperBound by mutableStateOf("")

    var lowerBound by mutableStateOf("")

    var startTime: LocalDate by mutableStateOf(LocalDate.now())
}
