package ru.wanket.exchange_trading_assistant.service

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import ru.wanket.exchange_trading_assistant.navigator.ServiceNavigator
import ru.wanket.exchange_trading_assistant.entity.data.RateBaseInfo
import ru.wanket.exchange_trading_assistant.entity.data.RateType
import ru.wanket.exchange_trading_assistant.repository.FavoritesRepository
import ru.wanket.exchange_trading_assistant.repository.RateRepository
import java.lang.StringBuilder

@HiltWorker
class RateListenerService @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val favoritesRepository: FavoritesRepository,
    private val rateRepository: RateRepository,
    private val notificationService: NotificationService,
    private val serviceNavigator: ServiceNavigator
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork() = withContext(Dispatchers.IO) {
        if (!notificationService.isNotificationsEnabled(applicationContext)) {
            return@withContext Result.success()
        }

        favoritesRepository.getActiveNotificationsFavorites().map {
            async {
                val historyMonthly = rateRepository.run {
                    return@run if (it.type == RateType.Crypto) {
                        getCryptoHistoryMonthlyFull(it.codeName)
                    } else {
                        getCompanyHistoryMonthlyFull(it.codeName)
                    }
                }.filter { (date, _) -> date >= it.startLookFrom }

                val today = rateRepository.run {
                    return@run if (it.type == RateType.Crypto) {
                        getCryptoHistoryFull(it.codeName)
                    } else {
                        getCompanyHistoryFull(it.codeName)
                    }.first().second
                }

                val maxMonthly = historyMonthly.maxOf { (_, value) -> value.high }
                val minMonthly = historyMonthly.minOf { (_, value) -> value.low }

                val isMaxByTime = today.high > maxMonthly
                val isMinByTime = today.low < minMonthly

                val isMaxByValue = if (it.upperBound > .0) today.high > it.upperBound else false
                val isMinByValue = if (it.lowerBound > .0) today.low < it.lowerBound else false

                if (!isMaxByTime && !isMinByTime && !isMaxByValue && !isMinByValue) {
                    return@async
                }

                val message = StringBuilder().apply {
                    if (isMaxByTime || isMaxByValue) {
                        listOf(
                            isMaxByTime to maxMonthly,
                            isMaxByValue to it.upperBound
                        ).forEach { (isTriggered, value) ->
                            if (isTriggered) {
                                append("Rate has been more than $value\n")
                            }
                        }

                        append("Max rate now ${today.high}")
                    }

                    if (isMinByTime || isMinByValue) {
                        listOf(
                            isMinByTime to minMonthly,
                            isMinByValue to it.lowerBound
                        ).forEach { (isTriggered, value) ->
                            if (isTriggered) {
                                append("Rate has been less than $value\n")
                            }
                        }

                        append("Min rate now ${today.low}")
                    }
                }.toString()

                notificationService.show(
                    applicationContext,
                    "Rate of ${it.codeName} ${it.type.displayName.lowercase()} changed",
                    message,
                    serviceNavigator.rateInfoActivityIntent(
                        applicationContext,
                        RateBaseInfo(it.codeName, it.type)
                    )
                )
            }
        }.awaitAll()

        return@withContext Result.success()
    }
}
