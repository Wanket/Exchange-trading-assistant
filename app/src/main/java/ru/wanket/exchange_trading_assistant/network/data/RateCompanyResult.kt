package ru.wanket.exchange_trading_assistant.network.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RateCompanyResult(
    @SerialName("Time Series (Daily)")
    val days: Map<String, RateCompanyDate>
)

@Serializable
class RateCompanyDate(
    @SerialName("4. close")
    val value: Double
)
