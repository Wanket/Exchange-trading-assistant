package ru.wanket.exchange_trading_assistant.repository

import ru.wanket.exchange_trading_assistant.db.DataBase
import ru.wanket.exchange_trading_assistant.db.Favorite
import ru.wanket.exchange_trading_assistant.entity.data.RateBaseInfo
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepository @Inject constructor(dataBase: DataBase) {
    private val favoriteDao = dataBase.favoriteDao()

    suspend fun addFavorite(rateBaseInfo: RateBaseInfo) = rateBaseInfo.run {
        favoriteDao.insert(Favorite(codeName, type, false, .0, .0, LocalDate.MIN))
    }

    suspend fun deleteFavorite(rateBaseInfo: RateBaseInfo) = rateBaseInfo.run {
        favoriteDao.deleteById(codeName, type)
    }

    suspend fun isFavorite(rateBaseInfo: RateBaseInfo) = rateBaseInfo.run {
        favoriteDao.exist(codeName, type)
    }
}
