package ru.wanket.exchange_trading_assistant.ui.rate_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ru.wanket.exchange_trading_assistant.entity.Crypto
import ru.wanket.exchange_trading_assistant.entity.RateFullInfo
import java.time.LocalDate

class RateInfoViewModel : ViewModel() {
    val rate by mutableStateOf<RateFullInfo>(Crypto("IBM", "IBM",
        listOf(LocalDate.now().minusDays(30) to 0.0, LocalDate.now() to 100.0),
        listOf(LocalDate.now() to 100.0, LocalDate.now().plusDays(30) to 250.0)))

    fun t() {
        rate.history
    }
}
