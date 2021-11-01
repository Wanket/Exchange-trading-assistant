package ru.wanket.exchange_trading_assistant.entity

import android.content.Context
import com.example.exchange_trading_assistant.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Singleton

class CryptoInfoLoader(context: Context) {
    @Serializable
    class CryptoInfos(val cryptos: List<Crypto>)

    @Serializable
    class Crypto(
        @SerialName("currency code")
        val code: String,
        @SerialName("currency name")
        val name: String
    )

    val baseInfos = Json.decodeFromStream<CryptoInfos>(context.resources.openRawResource(R.raw.cryptos))
}

@Module
@InstallIn(SingletonComponent::class)
object CryptoInfoLoaderModule {
    @Provides
    @Singleton
    fun loader(@ApplicationContext context: Context) = CryptoInfoLoader(context)
}
