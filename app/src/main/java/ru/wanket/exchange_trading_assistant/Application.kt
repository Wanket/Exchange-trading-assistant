package ru.wanket.exchange_trading_assistant

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import ru.wanket.exchange_trading_assistant.service.NotificationService
import ru.wanket.exchange_trading_assistant.service.ServiceManager
import javax.inject.Inject

@HiltAndroidApp
class Application : Application(), Configuration.Provider {
    @Inject
    lateinit var serviceManager: ServiceManager

    @Inject
    lateinit var notificationService: NotificationService

    @Inject lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        notificationService.load(this)

        serviceManager.load(this)
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder().setWorkerFactory(workerFactory).build()
}
