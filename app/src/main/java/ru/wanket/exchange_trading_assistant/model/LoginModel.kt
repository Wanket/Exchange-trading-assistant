package ru.wanket.exchange_trading_assistant.model

import ru.wanket.exchange_trading_assistant.entity.Settings
import javax.inject.Inject

class LoginModel @Inject constructor(private val settings: Settings) {
    val isPinCodeSetup = settings.pinCode != null

    fun checkPinCode(pinCode: Int) = settings.pinCode == pinCode

    fun createPinCode(pinCode: Int) {
        settings.pinCode = pinCode
    }
}
