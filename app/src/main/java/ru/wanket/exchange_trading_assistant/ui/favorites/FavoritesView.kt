package ru.wanket.exchange_trading_assistant.ui.favorites

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.wanket.exchange_trading_assistant.ui.theme.ExchangeTradingAssistantTheme
import ru.wanket.exchange_trading_assistant.ui.widgets.EndArrangementRow
import ru.wanket.exchange_trading_assistant.ui.widgets.RateBaseInfoView
import ru.wanket.exchange_trading_assistant.ui.widgets.TitleBar

@Composable
fun Activity.Ui(viewModel: FavoritesViewModel) {
    ExchangeTradingAssistantTheme {
        TitleBar {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(5.dp)
            ) {
                LazyColumn(contentPadding = PaddingValues(top = 8.dp)) {
                    items(viewModel.favorites) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { viewModel.onRateClicked(it) }
                        ) {
                            RateBaseInfoView(it)

                            EndArrangementRow {
                                IconButton(onClick = { viewModel.onEditClicked(it) }) {
                                    Icon(Icons.Default.Edit, null)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
