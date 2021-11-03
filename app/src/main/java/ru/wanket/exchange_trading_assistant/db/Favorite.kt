package ru.wanket.exchange_trading_assistant.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.wanket.exchange_trading_assistant.entity.data.RateType
import java.time.LocalDate

@Entity(primaryKeys = ["codeName", "type"])
@TypeConverters(FavoriteConverter::class)
class Favorite(
    val codeName: String,

    val type: RateType,

    @ColumnInfo
    val useNotifications: Boolean,

    @ColumnInfo
    val upperBound: Double,

    @ColumnInfo
    val lowerBound: Double,

    @ColumnInfo
    val startLookFrom: LocalDate,
)

@Dao
interface FavoriteDao {
    @Insert
    suspend fun insert(favorite: Favorite)

    @Query("DELETE FROM Favorite WHERE codeName = :codeName AND type = :type")
    suspend fun deleteById(codeName: String, type: RateType)

    @Update
    suspend fun update(favorite: Favorite)

    @Query("SELECT * FROM Favorite")
    fun getAllFavorites(): Flow<List<Favorite>>

    @Query("SELECT * FROM Favorite WHERE codeName = :codeName AND type = :type")
    suspend fun getById(codeName: String, type: RateType): Favorite

    @Query("SELECT EXISTS(SELECT * FROM Favorite WHERE codeName = :codeName AND type = :type)")
    suspend fun exist(codeName: String, type: RateType): Boolean

    @Query("SELECT * FROM Favorite WHERE useNotifications = 1")
    suspend fun getActiveNotificationsFavorites(): List<Favorite>
}

class FavoriteConverter {
    @TypeConverter
    fun dateTimeToTimestamp(date: LocalDate) = date.toEpochDay()

    @TypeConverter
    fun dateTimeFromTimeStamp(epochDay: Long): LocalDate = LocalDate.ofEpochDay(epochDay)
}
