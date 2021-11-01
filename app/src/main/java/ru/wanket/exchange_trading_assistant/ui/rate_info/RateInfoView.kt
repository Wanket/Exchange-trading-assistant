package ru.wanket.exchange_trading_assistant.ui.rate_info

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot
import ru.wanket.exchange_trading_assistant.entity.data.Company
import ru.wanket.exchange_trading_assistant.entity.data.toBaseInfo
import ru.wanket.exchange_trading_assistant.ui.theme.ExchangeTradingAssistantTheme
import ru.wanket.exchange_trading_assistant.ui.widgets.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun Ui(viewModel: RateInfoViewModel) {
    val isPressed = remember { mutableStateOf(false) }
    val xOffset = remember { mutableStateOf(0f) }
    val boxWidth = remember { mutableStateOf(0) }
    val selectedPoint = remember { mutableStateOf(0) }

    val densityOffset = LocalDensity.current.density
    val dataPattern = DateTimeFormatter.ofPattern("dd.MM")

    ExchangeTradingAssistantTheme {
        Scaffold(
            topBar = {
                TopAppBar {
                    RateBaseInfoView(viewModel.rate.toBaseInfo())

                    EndArrangementRow {
                        IconButton(
                            onClick = {
                                viewModel.apply {
                                    isFavorite = !isFavorite

                                    onFavoriteChanged()
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.run {
                                    if (viewModel.isFavorite) Favorite else FavoriteBorder
                                },
                                null
                            )
                        }
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                viewModel.rate.apply {
                    LabeledInfo("Full name", fullName)

                    if (this is Company) {
                        ExpandableColumn("Details") {
                            if (description.length > 150) {
                                ExpandableColumn("Description") {
                                    Text(description)
                                }
                            } else {
                                LabeledInfo("Description", description)
                            }

                            LabeledInfo("Registry country", registryCountry)
                            LabeledInfo("Currency type", currencyType)
                            LabeledInfo("Business scope", scope)
                            LabeledInfo("Legal address", address)
                            LabeledInfo("Fiscal year end", fiscalYearEnd)
                            LabeledInfo("Capitalization", capitalization.toString())
                            LabeledInfo("EBITDA", ebitda.toString())
                        }
                    }
                }

                if (viewModel.history.isNotEmpty()) {
                    val steps = viewModel.history.run {
                        first().first.toEpochDay() - last().first.toEpochDay()
                    }.toInt() + 15

                    Box(
                        Modifier.onGloballyPositioned {
                            boxWidth.value = it.size.width
                        }
                    ) {
                        LineGraph(
                            LinePlot(
                                listOf(
                                    LinePlot.Line(
                                        viewModel.history.toLine(),
                                        LinePlot.Connection(Color.Green),
                                        LinePlot.Intersection(Color.Green),
                                        areaUnderLine = LinePlot.AreaUnderLine(Color.Green, .1f)
                                    ),
                                    LinePlot.Line(
                                        viewModel.prediction.toLine(),
                                        LinePlot.Connection(Color.Red),
                                        LinePlot.Intersection(Color.Red)
                                    )
                                ),
                                LinePlot.Grid(Color.LightGray),
                                LinePlot.Selection(),
                                LinePlot.XAxis(
                                    steps = steps,
                                    unit = .5f,
                                ) { min, offset, _ ->
                                    Text("")

                                    for (item in 1 until steps) {
                                        val value = item * offset + min
                                        Text(
                                            text = LocalDate.ofEpochDay(value.toLong())
                                                .format(dataPattern),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            style = MaterialTheme.typography.caption
                                        )
                                    }
                                },
                                LinePlot.YAxis(paddingStart = 0.dp),
                                isZoomAllowed = false
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            onSelectionStart = { isPressed.value = true },
                            onSelectionEnd = { isPressed.value = false },
                        ) { x, points ->
                            val xCenter = if (x < boxWidth.value / 2f) {
                                boxWidth.value.toFloat() - 160 * densityOffset
                            } else {
                                55 * densityOffset
                            }

                            xOffset.value = xCenter
                            selectedPoint.value = points[0].x.toInt()
                        }

                        if (isPressed.value) {
                            ColumnWithRoundedBackground(
                                modifier = Modifier.graphicsLayer(translationX = xOffset.value),
                                alpha = 0.5f
                            ) {
                                val date = LocalDate.ofEpochDay(selectedPoint.value.toLong())
                                Text("Date: ${date.format(dataPattern)}")

                                viewModel.apply {
                                    if (prediction.isNotEmpty() && prediction[0].first < date) {
                                        Text("Predicted rate: ${prediction.find { it.first == date }?.second}")
                                    } else {
                                        history.find { it.first == date }?.let {
                                            Text("Been rate: ${it.second}")
                                        }
                                    }
                                }
                            }
                        } else {
                            ColumnWithRoundedBackground(
                                modifier = Modifier.graphicsLayer(translationX = boxWidth.value.toFloat() - 80 * densityOffset)
                            ) {
                                ShadowedText("History", color = Color.Green)
                                ShadowedText("Predicted", color = Color.Red)
                            }
                        }
                    }
                }
            }
        }

    }
}

private fun History.toLine() = map { (date, rate) ->
    DataPoint(date.toEpochDay().toFloat(), rate.toFloat())
}
