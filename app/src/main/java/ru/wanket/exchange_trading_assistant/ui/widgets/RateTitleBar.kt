package ru.wanket.exchange_trading_assistant.ui.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import ru.wanket.exchange_trading_assistant.entity.data.RateBaseInfo

@Composable
fun RateTitleBar(rateBaseInfo: RateBaseInfo, content: @Composable (PaddingValues) -> Unit) =
    Scaffold(
        topBar = {
            TopAppBar {
                RateBaseInfoView(rateBaseInfo)
            }
        },
        content = content
    )
