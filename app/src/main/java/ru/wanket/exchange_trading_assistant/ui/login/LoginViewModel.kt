package ru.wanket.exchange_trading_assistant.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.wanket.exchange_trading_assistant.entity.Settings
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val settings: Settings) : ViewModel() {
    val isLogin = settings.pinCode != null

    var pinCode by mutableStateOf("")

    var isPinCodeWrong by mutableStateOf(false)

    var onLoginSuccess = {}

    fun onLogin() {
        if (!isLogin) {
            settings.pinCode = pinCode.toInt()

            onLoginSuccess()

            return
        }

        if (settings.pinCode != pinCode.toInt()) {
            isPinCodeWrong = true

            return
        }

        onLoginSuccess()
    }
}
