package ru.wanket.exchange_trading_assistant.network.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RateCryptoResult(
    @SerialName("Time Series (Digital Currency Daily)")
    val days: Map<String, RateCryptoDate>
)

@Serializable
class RateCryptoDate(
    @SerialName("4b. close (USD)")
    val value: Double
)
