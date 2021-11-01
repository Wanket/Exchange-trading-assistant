package ru.wanket.exchange_trading_assistant.ui.login

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.wanket.exchange_trading_assistant.view_models.LoginViewModel

@AndroidEntryPoint
class LoginActivity : FragmentActivity() {
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { Ui(viewModel) }
    }
}
