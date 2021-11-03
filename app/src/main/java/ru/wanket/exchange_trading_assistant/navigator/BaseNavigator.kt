package ru.wanket.exchange_trading_assistant.navigator

import android.content.Intent
import androidx.activity.ComponentActivity
import ru.wanket.exchange_trading_assistant.entity.data.RateBaseInfo
import ru.wanket.exchange_trading_assistant.entity.data.RateType

abstract class RateBaseInfoActivity : ComponentActivity() {
    protected fun Intent.getRate() = RateBaseInfo(
        getStringExtra(IntentConstants.RATE_ID)!!,
        RateType.values()[getIntExtra(IntentConstants.RATE_TYPE, -1)]
    )
}

private object IntentConstants {
    const val RATE_ID = "rate_id"
    const val RATE_TYPE = "rate_type"
}

abstract class BaseNavigator {
    protected fun Intent.putRate(rateBaseInfo: RateBaseInfo) = rateBaseInfo.apply {
        putExtra(IntentConstants.RATE_ID, codeName)
        putExtra(IntentConstants.RATE_TYPE, type.ordinal)
    }
}
