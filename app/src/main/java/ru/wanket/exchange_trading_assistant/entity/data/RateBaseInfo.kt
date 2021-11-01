package ru.wanket.exchange_trading_assistant.entity.data

class RateBaseInfo(
    val codeName: String,
    val type: RateType
) : Comparable<RateBaseInfo> {
    override fun compareTo(other: RateBaseInfo) = codeName.compareTo(other.codeName).let {
        if (it != 0) it else type.compareTo(other.type)
    }
}
