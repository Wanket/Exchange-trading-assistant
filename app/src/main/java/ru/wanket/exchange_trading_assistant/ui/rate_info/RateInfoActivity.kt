package ru.wanket.exchange_trading_assistant.ui.rate_info

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

class RateInfoActivity : ComponentActivity() {
    companion object {
        const val RATE_ID = "rate_id"
        const val RATE_TYPE = "rate_type"
    }

    private val viewModel by viewModels<RateInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { Ui(viewModel) }
    }
}
