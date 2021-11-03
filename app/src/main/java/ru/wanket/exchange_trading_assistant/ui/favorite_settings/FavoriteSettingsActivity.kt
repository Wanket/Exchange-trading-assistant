package ru.wanket.exchange_trading_assistant.ui.favorite_settings

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.wanket.exchange_trading_assistant.navigator.RateBaseInfoActivity
import ru.wanket.exchange_trading_assistant.view_model.FavoriteSettingsViewModel

@AndroidEntryPoint
class FavoriteSettingsActivity : RateBaseInfoActivity() {
    private val viewModel by viewModels<FavoriteSettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.rateBaseInfo = intent.getRate()

        setContent { Ui(viewModel) }
    }
}
