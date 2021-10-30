package ru.wanket.exchange_trading_assistant.entity

import android.content.Context
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

class Settings(context: Context) {
    companion object {
        private const val PIN_KEY = "pin_key"
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var pinCode: Int?
        set(value) = preferences.edit().run {
            putInt(PIN_KEY, value ?: -1)
            apply()
        }
        get() = preferences.getInt(PIN_KEY, -1).let { if (it == -1) null else it }
}

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {
    @Provides
    @Singleton
    fun settings(@ApplicationContext context: Context) = Settings(context)
}
