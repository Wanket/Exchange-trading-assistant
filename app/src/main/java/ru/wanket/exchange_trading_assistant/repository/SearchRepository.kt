package ru.wanket.exchange_trading_assistant.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import ru.wanket.exchange_trading_assistant.entity.CryptoInfoLoader
import ru.wanket.exchange_trading_assistant.entity.RateBaseInfo
import ru.wanket.exchange_trading_assistant.entity.RateType
import ru.wanket.exchange_trading_assistant.network.AlphaVantageClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val client: AlphaVantageClient,
    private val cryptoInfoLoader: CryptoInfoLoader
) {
    suspend fun getRateBaseInfos(scope: CoroutineScope, keywords: String): List<RateBaseInfo> {
        val companies = scope.async { client.searchCompany(keywords) }

        val cryptos = cryptoInfoLoader.infos.cryptos
            .filter { it.code.contains(keywords, true) || it.name.contains(keywords, true) }
            .map { RateBaseInfo(it.code, RateType.Crypto) }

        return companies.await().bestMatches
            .map { RateBaseInfo(it.symbol, RateType.Company) }
            .toMutableList()
            .apply {
                addAll(cryptos)
                sort()
            }
    }
}
