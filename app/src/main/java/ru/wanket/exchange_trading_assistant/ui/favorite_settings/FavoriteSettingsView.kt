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
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import ru.wanket.exchange_trading_assistant.ui.theme.ExchangeTradingAssistantTheme
import ru.wanket.exchange_trading_assistant.ui.widgets.*
import ru.wanket.exchange_trading_assistant.view_model.FavoriteSettingsViewModel
import java.time.LocalDate

@Composable
fun Ui(viewModel: FavoriteSettingsViewModel) {
    val dialogState = rememberMaterialDialogState()

    ExchangeTradingAssistantTheme {
        RateTitleBar(viewModel.rateBaseInfo) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                viewModel.apply {
                    if (rateBaseInfo.codeName.isEmpty()) {
                        return@Column
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Enable notifications")

                        EndArrangementRow {
                            Checkbox(
                                checked = isEnabled,
                                onCheckedChange = {
                                    isEnabled = it

                                    onSettingsChanged()
                                }
                            )
                        }
                    }

                    FilteredTextField(
                        enabled = isEnabled,
                        label = { Text("Upper bound") },
                        modifier = Modifier.fillMaxWidth(),
                        value = upperBound,
                        onValueChange = {
                            upperBound = it

                            if (it.isNotEmpty()) {
                                onSettingsChanged()
                            }
                        },
                        filter = ::isPositiveDoubleFilter,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    FilteredTextField(
                        enabled = isEnabled,
                        label = { Text("Lower bound") },
                        modifier = Modifier.fillMaxWidth(),
                        value = lowerBound,
                        onValueChange = {
                            lowerBound = it

                            if (it.isNotEmpty()) {
                                onSettingsChanged()
                            }
                        },
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
                        val year = LocalDate.now().year

                        datepicker(yearRange = year - 1..year) {
                            startTime = it

                            onSettingsChanged()
                        }
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
}
