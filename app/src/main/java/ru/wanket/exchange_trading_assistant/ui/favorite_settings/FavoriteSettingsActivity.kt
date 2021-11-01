package ru.wanket.exchange_trading_assistant.ui.favorite_settings

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import ru.wanket.exchange_trading_assistant.RateBaseInfoActivity
import ru.wanket.exchange_trading_assistant.view_models.FavoriteSettingsViewModel

class FavoriteSettingsActivity : RateBaseInfoActivity() {
    private val viewModel by viewModels<FavoriteSettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { Ui(viewModel) }
    }
}
