package ru.wanket.exchange_trading_assistant.network

import android.content.Context
import com.example.exchange_trading_assistant.R
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import javax.inject.Singleton

class AlphaVantageClient(private val apiKey: String) {
    private val json = Json { ignoreUnknownKeys = true }

    private val client = Retrofit.Builder().run {
        baseUrl("https://www.alphavantage.co/")
        addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
        build().create(AlphaVantageApi::class.java)
    }

    suspend fun searchCompany(keywords: String) = client.searchCompany(keywords, apiKey)

    suspend fun companyInfo(companyName: String) = client.companyInfo(companyName, apiKey)

    suspend fun rateCompany(companyName: String) = client.rateCompany(companyName, apiKey)

    suspend fun rateCrypto(cryptoName: String) = client.rateCrypto(cryptoName, apiKey)

    suspend fun rateCompanyMonthly(companyName: String) =
        client.rateCompanyMonthly(companyName, apiKey)

    suspend fun rateCryptoMonthly(cryptoName: String) = client.rateCryptoMonthly(cryptoName, apiKey)
}

@Module
@InstallIn(SingletonComponent::class)
class AlphaVantageClientModule {
    @Provides
    @Singleton
    fun client(@ApplicationContext context: Context) =
        AlphaVantageClient(context.getString(R.string.api_key))
}
