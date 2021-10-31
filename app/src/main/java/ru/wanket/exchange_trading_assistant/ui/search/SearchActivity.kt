package ru.wanket.exchange_trading_assistant.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.wanket.exchange_trading_assistant.entity.RateBaseInfo
import ru.wanket.exchange_trading_assistant.ui.rate_info.RateInfoActivity

@AndroidEntryPoint
class SearchActivity : ComponentActivity() {
    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.onRateClicked = this::onRateClicked

        setContent { Ui(viewModel) }
    }

    private fun onRateClicked(rateBaseInfo: RateBaseInfo) {
        startActivity(Intent(this, RateInfoActivity::class.java).apply {
            putExtra(RateInfoActivity.RATE_ID, rateBaseInfo.codeName)
            putExtra(RateInfoActivity.RATE_TYPE, rateBaseInfo.type.ordinal)
        })
    }
}
