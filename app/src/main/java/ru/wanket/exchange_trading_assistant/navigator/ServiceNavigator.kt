package ru.wanket.exchange_trading_assistant.navigator

import android.content.Context
import android.content.Intent
import ru.wanket.exchange_trading_assistant.entity.data.RateBaseInfo
import ru.wanket.exchange_trading_assistant.ui.rate_info.RateInfoActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceNavigator @Inject constructor() : BaseNavigator() {
    fun rateInfoActivityIntent(context: Context, rateBaseInfo: RateBaseInfo) =
        Intent(context, RateInfoActivity::class.java).apply { putRate(rateBaseInfo) }
}
