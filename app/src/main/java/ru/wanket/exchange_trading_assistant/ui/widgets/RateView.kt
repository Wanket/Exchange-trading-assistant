package ru.wanket.exchange_trading_assistant.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.wanket.exchange_trading_assistant.entity.RateBaseInfo

@Composable
fun RateView(rateBaseInfo: RateBaseInfo) {
    Column(modifier = Modifier.padding(start = 8.dp)) {
        Text(rateBaseInfo.codeName)
        Text(rateBaseInfo.type.displayName, fontSize = 8.sp)
    }
}
