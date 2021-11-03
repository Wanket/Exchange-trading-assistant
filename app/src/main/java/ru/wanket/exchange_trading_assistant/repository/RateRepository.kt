package ru.wanket.exchange_trading_assistant.repository

import ru.wanket.exchange_trading_assistant.entity.CryptoInfoLoader
import ru.wanket.exchange_trading_assistant.entity.data.Company
import ru.wanket.exchange_trading_assistant.entity.data.Crypto
import ru.wanket.exchange_trading_assistant.entity.data.GeneralFullRate
import ru.wanket.exchange_trading_assistant.network.AlphaVantageClient
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RateRepository @Inject constructor(
    private val client: AlphaVantageClient,
    private val cryptoInfoLoader: CryptoInfoLoader
) {
    fun getCryptoInfo(codeName: String) =
        cryptoInfoLoader.baseInfos.cryptos.find { it.code == codeName }?.run { Crypto(code, name) }

    suspend fun getCryptoHistory(codeName: String) = client.rateCrypto(codeName).days.map {
        LocalDate.parse(it.key) to it.value.value
    }

    suspend fun getCryptoHistoryFull(codeName: String) = client.rateCrypto(codeName).days.map {
        LocalDate.parse(it.key) to it.value.run { GeneralFullRate(high, low, value) }
    }

    suspend fun getCryptoHistoryMonthlyFull(codeName: String) =
        client.rateCryptoMonthly(codeName).days.map {
            LocalDate.parse(it.key) to it.value.run { GeneralFullRate(high, low, value) }
        }

    suspend fun getFullCompanyInfo(codeName: String) = client.companyInfo(codeName).run {
        Company(
            code,
            fullName,
            description,
            registryCountry,
            currencyType,
            scope,
            address,
            fiscalYearEnd,
            capitalization,
            ebitda
        )
    }

    suspend fun getCompanyHistory(codeName: String) = client.rateCompany(codeName).days.map {
        LocalDate.parse(it.key) to it.value.value
    }

    suspend fun getCompanyHistoryFull(codeName: String) = client.rateCompany(codeName).days.map {
        LocalDate.parse(it.key) to it.value.run { GeneralFullRate(high, low, value) }
    }

    suspend fun getCompanyHistoryMonthlyFull(codeName: String) =
        client.rateCompanyMonthly(codeName).days.map {
            LocalDate.parse(it.key) to it.value.run { GeneralFullRate(high, low, value) }
        }
}
