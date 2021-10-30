package ru.wanket.exchange_trading_assistant.ui.rate_info

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import ru.wanket.exchange_trading_assistant.entity.Company
import ru.wanket.exchange_trading_assistant.entity.toBaseInfo
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
        RateTitleBar(viewModel.rate.toBaseInfo()) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                viewModel.rate.apply {
                    LabeledInfo("Full name", fullName)

                    if (this is Company) {
                        ExpandableColumn("Details") {
                            LabeledInfo("Description", description)
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

                Box(Modifier.onGloballyPositioned {
                    boxWidth.value = it.size.width
                }) {
                    LineGraph(
                        LinePlot(
                            listOf(
                                LinePlot.Line(
                                    viewModel.rate.history.toLine(),
                                    LinePlot.Connection(Color.Green),
                                    LinePlot.Intersection(Color.Green),
                                    areaUnderLine = LinePlot.AreaUnderLine(Color.Green, .1f)
                                ),
                                LinePlot.Line(
                                    viewModel.rate.prediction.toLine(),
                                    LinePlot.Connection(Color.Red),
                                    LinePlot.Intersection(Color.Red)
                                )
                            ),
                            LinePlot.Grid(Color.LightGray),
                            LinePlot.Selection(),
                            LinePlot.XAxis(
                                steps = 11,
                                unit = 3f,
                            ) { min, offset, _ ->
                                Text("")

                                for (item in 1 until 10) {
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
                            boxWidth.value.toFloat() - 150 * densityOffset
                        } else {
                            40 * densityOffset
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

                            viewModel.rate.apply {
                                if (prediction[0].first < date) {
                                    Text("Predicted rate: ${prediction.find { it.first == date }?.second}")
                                } else {
                                    Text("Been rate: ${history.find { it.first == date }?.second}")
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

private fun List<Pair<LocalDate, Double>>.toLine() = map { (date, rate) ->
    DataPoint(date.toEpochDay().toFloat(), rate.toFloat())
}
