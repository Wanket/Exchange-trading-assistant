package ru.wanket.exchange_trading_assistant.network.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DailyRateCompanyResult(
    @SerialName("Time Series (Daily)")
    val days: Map<String, RateCompanyDate>
)

@Serializable
class MonthlyRateCompanyResult(
    @SerialName("Monthly Time Series")
    val days: Map<String, RateCompanyDate>
)

@Serializable
class RateCompanyDate(
    @SerialName("2. high")
    val high: Double,
    @SerialName("3. low")
    val low: Double,
    @SerialName("4. close")
    val value: Double
)
