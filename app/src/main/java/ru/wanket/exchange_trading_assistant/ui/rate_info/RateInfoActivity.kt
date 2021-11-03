package ru.wanket.exchange_trading_assistant.ui.rate_info

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.wanket.exchange_trading_assistant.navigator.RateBaseInfoActivity
import ru.wanket.exchange_trading_assistant.view_model.RateInfoViewModel

@AndroidEntryPoint
class RateInfoActivity : RateBaseInfoActivity() {
    private val viewModel by viewModels<RateInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.rateBaseInfo = intent.getRate()

        setContent { Ui(viewModel) }
    }
}
