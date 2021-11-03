package ru.wanket.exchange_trading_assistant.repository

import kotlinx.coroutines.flow.map
import ru.wanket.exchange_trading_assistant.db.DataBase
import ru.wanket.exchange_trading_assistant.db.Favorite
import ru.wanket.exchange_trading_assistant.entity.data.FavoriteSettings
import ru.wanket.exchange_trading_assistant.entity.data.RateBaseInfo
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepository @Inject constructor(dataBase: DataBase) {
    private val favoriteDao = dataBase.favoriteDao()

    suspend fun addFavorite(rateBaseInfo: RateBaseInfo) = rateBaseInfo.run {
        favoriteDao.insert(Favorite(codeName, type, false, .0, .0, LocalDate.now()))
    }

    suspend fun updateFavorite(rateBaseInfo: RateBaseInfo, favorite: FavoriteSettings) =
        favoriteDao.update(
            Favorite(
                rateBaseInfo.codeName,
                rateBaseInfo.type,
                favorite.isEnabled,
                favorite.upperBound,
                favorite.lowerBound,
                favorite.startDate
            )
        )

    suspend fun deleteFavorite(rateBaseInfo: RateBaseInfo) = rateBaseInfo.run {
        favoriteDao.deleteById(codeName, type)
    }

    suspend fun isFavorite(rateBaseInfo: RateBaseInfo) = rateBaseInfo.run {
        favoriteDao.exist(codeName, type)
    }

    fun getFavoriteRates() = favoriteDao.getAllFavorites().map {
        it.map { favorite -> RateBaseInfo(favorite.codeName, favorite.type) }
    }

    suspend fun getFavoriteSettings(rateBaseInfo: RateBaseInfo) = rateBaseInfo.run {
        favoriteDao.getById(codeName, type).run {
            FavoriteSettings(useNotifications, upperBound, lowerBound, startLookFrom)
        }
    }

    suspend fun getActiveNotificationsFavorites() = favoriteDao.getActiveNotificationsFavorites()
}
