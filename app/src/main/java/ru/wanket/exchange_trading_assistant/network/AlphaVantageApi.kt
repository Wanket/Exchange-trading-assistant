package ru.wanket.exchange_trading_assistant.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.wanket.exchange_trading_assistant.network.data.*

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
    ): DailyRateCompanyResult

    @GET("query?function=TIME_SERIES_MONTHLY&market=USD")
    suspend fun rateCompanyMonthly(
        @Query("symbol") cryptoName: String,
        @Query("apikey") apiKey: String
    ): MonthlyRateCompanyResult

    @GET("query?function=DIGITAL_CURRENCY_DAILY&market=USD")
    suspend fun rateCrypto(
        @Query("symbol") cryptoName: String,
        @Query("apikey") apiKey: String
    ): DailyRateCryptoResult

    @GET("query?function=DIGITAL_CURRENCY_MONTHLY&market=USD")
    suspend fun rateCryptoMonthly(
        @Query("symbol") cryptoName: String,
        @Query("apikey") apiKey: String
    ): MonthlyRateCryptoResult
}
