package ru.wanket.exchange_trading_assistant.entity.data

sealed class RateFullInfo(
    var codeName: String,
    var fullName: String,
)

class Crypto(
    codeName: String,
    fullName: String,
) : RateFullInfo(codeName, fullName)

class Company(
    codeName: String,
    fullName: String,
    val description: String,
    val registryCountry: String,
    val currencyType: String,
    val scope: String,
    val address: String,
    val fiscalYearEnd: String,
    val capitalization: Long,
    val ebitda: Long
) : RateFullInfo(codeName, fullName)

fun RateFullInfo.toBaseInfo() = when (this) {
    is Crypto -> RateBaseInfo(codeName, RateType.Crypto)
    is Company -> RateBaseInfo(codeName, RateType.Company)
}
