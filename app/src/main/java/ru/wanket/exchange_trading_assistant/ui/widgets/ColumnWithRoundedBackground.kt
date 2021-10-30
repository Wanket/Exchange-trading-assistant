package ru.wanket.exchange_trading_assistant.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColumnWithRoundedBackground(
    modifier: Modifier = Modifier,
    alpha: Float = 0.25f,
    content: @Composable ColumnScope.() -> Unit
) = Column(
    modifier = modifier
        .background(
            Color.Gray.copy(alpha = alpha),
            RoundedCornerShape(8.dp)
        )
        .padding(4.dp),
    content = content
)
