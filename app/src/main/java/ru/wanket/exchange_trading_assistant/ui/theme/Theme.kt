package ru.wanket.exchange_trading_assistant.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@Composable
fun ExchangeTradingAssistantTheme(content: @Composable () -> Unit) = Surface {
    MaterialTheme(
        colors = lightColors(),
        content = content
    )
}
