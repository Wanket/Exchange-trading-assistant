package ru.wanket.exchange_trading_assistant.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ShadowedText(text: String, color: Color) = Box {
    Text(
        text, color = Color.DarkGray, modifier = Modifier
            .offset(1.dp, 1.dp)
            .alpha(0.5f)
    )
    Text(text, color = color)
}
