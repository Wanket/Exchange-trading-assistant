package ru.wanket.exchange_trading_assistant.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.wanket.exchange_trading_assistant.model.LoginModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val model: LoginModel) : ViewModel() {
    val isLogin = model.isPinCodeSetup

    var pinCode by mutableStateOf("")

    var isPinCodeWrong by mutableStateOf(false)

    var onLoginSuccess by mutableStateOf({})

    fun onLogin() {
        if (!isLogin) {
            model.createPinCode(pinCode.toInt())

            onLoginSuccess()

            return
        }

        if (!model.checkPinCode(pinCode.toInt())) {
            isPinCodeWrong = true

            return
        }

        onLoginSuccess()
    }
}
