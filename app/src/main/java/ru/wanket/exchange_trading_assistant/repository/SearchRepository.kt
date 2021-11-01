package ru.wanket.exchange_trading_assistant.repository

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import ru.wanket.exchange_trading_assistant.entity.CryptoInfoLoader
import ru.wanket.exchange_trading_assistant.entity.data.RateBaseInfo
import ru.wanket.exchange_trading_assistant.entity.data.RateType
import ru.wanket.exchange_trading_assistant.network.AlphaVantageClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val client: AlphaVantageClient,
    private val cryptoInfoLoader: CryptoInfoLoader
) {
    suspend fun getRateBaseInfos(keywords: String): List<RateBaseInfo> = coroutineScope {
        val companies = async { client.searchCompany(keywords) }

        val cryptos = cryptoInfoLoader.baseInfos.cryptos
            .filter { it.code.contains(keywords, true) || it.name.contains(keywords, true) }
            .map { RateBaseInfo(it.code, RateType.Crypto) }

        return@coroutineScope companies.await().bestMatches
            .map { RateBaseInfo(it.symbol, RateType.Company) }
            .toMutableList()
            .apply {
                addAll(cryptos)
                sort()
            }
    }
}
