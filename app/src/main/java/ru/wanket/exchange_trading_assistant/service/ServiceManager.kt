package ru.wanket.exchange_trading_assistant.service

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.time.Duration
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceManager @Inject constructor() {
    fun load(context: Context) {
        WorkManager.getInstance(context).apply {
            enqueueUniquePeriodicWork(
                RateListenerService::class.qualifiedName!!,
                ExistingPeriodicWorkPolicy.KEEP,
                PeriodicWorkRequestBuilder<RateListenerService>(Duration.ofDays(1)).build()
            )
        }
    }
}
