package ru.wanket.exchange_trading_assistant.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.wanket.exchange_trading_assistant.entity.data.RateBaseInfo
import ru.wanket.exchange_trading_assistant.ui.putRate
import ru.wanket.exchange_trading_assistant.ui.rate_info.RateInfoActivity

@AndroidEntryPoint
class SearchActivity : ComponentActivity() {
    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.onRateClicked = this::onRateClicked

        setContent { Ui(viewModel) }
    }

    private fun onRateClicked(rateBaseInfo: RateBaseInfo) = startActivity(
        Intent(this, RateInfoActivity::class.java).apply { putRate(rateBaseInfo) }
    )
}
