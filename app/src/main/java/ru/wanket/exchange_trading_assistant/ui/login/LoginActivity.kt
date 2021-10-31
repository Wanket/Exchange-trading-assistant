package ru.wanket.exchange_trading_assistant.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.wanket.exchange_trading_assistant.ui.favorites.FavoritesActivity

@AndroidEntryPoint
class LoginActivity : FragmentActivity() {
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.onLoginSuccess = this::onLoginSuccess

        setContent { Ui(viewModel) }
    }

    private fun onLoginSuccess() = startActivity(Intent(this, FavoritesActivity::class.java))
}
