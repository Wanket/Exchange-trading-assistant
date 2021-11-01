package ru.wanket.exchange_trading_assistant.network.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class FullCompanyInfoResult(
    @SerialName("Symbol")
    val code: String,
    @SerialName("Name")
    val fullName: String,
    @SerialName("Description")
    val description: String,
    @SerialName("Country")
    val registryCountry: String,
    @SerialName("Currency")
    val currencyType: String,
    @SerialName("Industry")
    val scope: String,
    @SerialName("Address")
    val address: String,
    @SerialName("FiscalYearEnd")
    val fiscalYearEnd: String,
    @SerialName("MarketCapitalization")
    val capitalization: Long,
    @SerialName("EBITDA")
    val ebitda: Long
)
