package ru.wanket.exchange_trading_assistant.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ru.wanket.exchange_trading_assistant.ui.theme.ExchangeTradingAssistantTheme
import ru.wanket.exchange_trading_assistant.ui.widgets.RateBaseInfoView

@Composable
fun Ui(viewModel: SearchViewModel) {
    val focusManager = LocalFocusManager.current

    ExchangeTradingAssistantTheme {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(5.dp)
        ) {
            OutlinedTextField(
                value = viewModel.searchField,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                onValueChange = {
                    viewModel.apply {
                        searchField = it

                        onSearchFieldChanged(it)
                    }
                },
                trailingIcon = {
                    Icon(
                        Icons.Default.Search,
                        null,
                        modifier = Modifier.clickable { focusManager.clearFocus() }
                    )
                },
                keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() })
            )

            LazyColumn(contentPadding = PaddingValues(top = 8.dp)) {
                items(viewModel.foundedList) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.onRateClicked(it) }
                    ) {
                        RateBaseInfoView(it)
                    }
                }
            }
        }
    }
}
