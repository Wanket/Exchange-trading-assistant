package ru.wanket.exchange_trading_assistant.network

import retrofit2.http.GET
import retrofit2.http.Query

interface AlphaVantageApi {
    @GET("query?function=SYMBOL_SEARCH")
    suspend fun searchCompany(@Query("keywords") keywords: String, @Query("apikey") apiKey: String): SearchResult

    @GET("query?function=OVERVIEW")
    suspend fun companyInfo(@Query("symbol") companyName: String, @Query("apikey") apiKey: String)

    @GET("query?function=TIME_SERIES_DAILY&outputsize=compact")
    suspend fun rateCompany(@Query("symbol") companyName: String, @Query("apikey") apiKey: String)

    @GET("query?function=DIGITAL_CURRENCY_DAILY&market=USD")
    suspend fun rateCrypto(@Query("symbol") companyName: String, @Query("apikey") apiKey: String)
}
