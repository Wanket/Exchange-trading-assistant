package ru.wanket.exchange_trading_assistant.ui.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.wanket.exchange_trading_assistant.view_model.SearchViewModel

@AndroidEntryPoint
class SearchActivity : ComponentActivity() {
    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { Ui(viewModel) }
    }
}
