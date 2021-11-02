package ru.wanket.exchange_trading_assistant.entity.data

import java.time.LocalDate

class FavoriteSettings(
    var isEnabled: Boolean,
    var upperBound: Double,
    var lowerBound: Double,
    var startDate: LocalDate
)
