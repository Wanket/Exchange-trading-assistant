package ru.wanket.exchange_trading_assistant.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.wanket.exchange_trading_assistant.network.data.FullCompanyInfoResult
import ru.wanket.exchange_trading_assistant.network.data.RateCompanyResult
import ru.wanket.exchange_trading_assistant.network.data.RateCryptoResult
import ru.wanket.exchange_trading_assistant.network.data.SearchResult

interface AlphaVantageApi {
    @GET("query?function=SYMBOL_SEARCH")
    suspend fun searchCompany(
        @Query("keywords") keywords: String,
        @Query("apikey") apiKey: String
    ): SearchResult

    @GET("query?function=OVERVIEW")
    suspend fun companyInfo(
        @Query("symbol") companyName: String,
        @Query("apikey") apiKey: String
    ): FullCompanyInfoResult

    @GET("query?function=TIME_SERIES_DAILY")
    suspend fun rateCompany(
        @Query("symbol") companyName: String,
        @Query("apikey") apiKey: String
    ): RateCompanyResult

    @GET("query?function=DIGITAL_CURRENCY_DAILY&market=USD")
    suspend fun rateCrypto(
        @Query("symbol") cryptoName: String,
        @Query("apikey") apiKey: String
    ): RateCryptoResult
}
