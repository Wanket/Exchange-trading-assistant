package ru.wanket.exchange_trading_assistant.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SearchResult(
    val bestMatches: List<Match>
)

@Serializable
class Match(
    @SerialName("1. symbol")
    val symbol: String
)
