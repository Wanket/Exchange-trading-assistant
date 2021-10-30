package ru.wanket.exchange_trading_assistant.ui.favorite_settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import ru.wanket.exchange_trading_assistant.ui.theme.ExchangeTradingAssistantTheme
import ru.wanket.exchange_trading_assistant.ui.widgets.*

@Composable
fun Ui(viewModel: FavoriteSettingsViewModel) {
    val dialogState = rememberMaterialDialogState()

    ExchangeTradingAssistantTheme {
        RateTitleBar(viewModel.rate) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Enable notifications")

                    EndArrangementRow {
                        Checkbox(
                            checked = viewModel.isEnabled,
                            onCheckedChange = { viewModel.isEnabled = it }
                        )
                    }
                }

                FilteredTextField(
                    enabled = viewModel.isEnabled,
                    label = { Text("Upper bound") },
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.upperBound,
                    onValueChange = { viewModel.upperBound = it },
                    filter = ::isPositiveDoubleFilter,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                FilteredTextField(
                    enabled = viewModel.isEnabled,
                    label = { Text("Lower bound") },
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.lowerBound,
                    onValueChange = { viewModel.lowerBound = it },
                    filter = ::isPositiveDoubleFilter,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                MaterialDialog(
                    dialogState = dialogState,
                    buttons = {
                        positiveButton("Ok")
                        negativeButton("Cancel")
                    }
                ) {
                    datepicker { viewModel.startTime = it }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Start look from")

                    EndArrangementRow {
                        OutlinedButton(
                            onClick = { dialogState.show() },
                            enabled = viewModel.isEnabled
                        ) {
                            Text(viewModel.startTime.toString())
                        }
                    }
                }
            }
        }
    }
}
