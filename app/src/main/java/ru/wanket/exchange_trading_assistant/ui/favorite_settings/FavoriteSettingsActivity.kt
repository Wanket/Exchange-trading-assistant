package ru.wanket.exchange_trading_assistant.ui.favorite_settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

class FavoriteSettingsActivity : ComponentActivity() {
    private val viewModel by viewModels<FavoriteSettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { Ui(viewModel) }
    }
}
