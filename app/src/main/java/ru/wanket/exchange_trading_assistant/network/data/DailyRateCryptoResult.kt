package ru.wanket.exchange_trading_assistant.network.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DailyRateCryptoResult(
    @SerialName("Time Series (Digital Currency Daily)")
    val days: Map<String, RateCryptoDate>
)

@Serializable
class MonthlyRateCryptoResult(
    @SerialName("Time Series (Digital Currency Monthly)")
    val days: Map<String, RateCryptoDate>
)

@Serializable
class RateCryptoDate(
    @SerialName("2b. high (USD)")
    val high: Double,
    @SerialName("3b. low (USD)")
    val low: Double,
    @SerialName("4b. close (USD)")
    val value: Double
)
