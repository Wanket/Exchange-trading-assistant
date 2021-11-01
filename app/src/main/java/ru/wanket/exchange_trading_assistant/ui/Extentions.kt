package ru.wanket.exchange_trading_assistant.ui

import android.content.Intent
import ru.wanket.exchange_trading_assistant.entity.data.RateBaseInfo
import ru.wanket.exchange_trading_assistant.entity.data.RateType

private object IntentConstants {
    const val RATE_ID = "rate_id"
    const val RATE_TYPE = "rate_type"
}

fun Intent.putRate(rateBaseInfo: RateBaseInfo) = rateBaseInfo.apply {
    putExtra(IntentConstants.RATE_ID, codeName)
    putExtra(IntentConstants.RATE_TYPE, type.ordinal)
}

fun Intent.getRate() = RateBaseInfo(
    getStringExtra(IntentConstants.RATE_ID)!!,
    RateType.values()[getIntExtra(IntentConstants.RATE_TYPE, -1)]
)
