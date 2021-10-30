package ru.wanket.exchange_trading_assistant.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun LabeledInfo(label: String, text: String) = Column {
    Text(label, fontSize = 8.sp, fontWeight = FontWeight.Bold)
    Text(text)
}
