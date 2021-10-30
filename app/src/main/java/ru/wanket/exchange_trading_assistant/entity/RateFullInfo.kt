package ru.wanket.exchange_trading_assistant.entity

import java.time.LocalDate

sealed class RateFullInfo(
    val codeName: String,
    val fullName: String,
    val history: List<Pair<LocalDate, Double>>,
    val prediction: List<Pair<LocalDate, Double>>
)

class Crypto(
    codeName: String,
    fullName: String,
    history: List<Pair<LocalDate, Double>>,
    prediction: List<Pair<LocalDate, Double>>
) : RateFullInfo(codeName, fullName, history, prediction)

class Company(
    codeName: String,
    fullName: String,
    history: List<Pair<LocalDate, Double>>,
    prediction: List<Pair<LocalDate, Double>>,
    val description: String,
    val registryCountry: String,
    val currencyType: String,
    val scope: String,
    val address: String,
    val fiscalYearEnd: String,
    val capitalization: Double,
    val ebitda: Double
) : RateFullInfo(codeName, fullName, history, prediction)

fun RateFullInfo.toBaseInfo() = when (this) {
    is Crypto -> RateBaseInfo(codeName, RateType.Crypto)
    is Company -> RateBaseInfo(codeName, RateType.Company)
}
