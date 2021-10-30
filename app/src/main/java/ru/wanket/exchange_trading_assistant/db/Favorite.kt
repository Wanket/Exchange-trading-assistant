package ru.wanket.exchange_trading_assistant.db

import androidx.room.*
import ru.wanket.exchange_trading_assistant.entity.RateType
import java.time.LocalDateTime
import java.time.OffsetDateTime

@Entity(primaryKeys = ["codeName", "type"])
@TypeConverters(FavoriteConverter::class, RateTypeConverter::class)
class Favorite(
    val codeName: Int,

    val type: RateType,

    @ColumnInfo
    val useNotifications: Boolean,

    @ColumnInfo
    val upperBound: Double,

    @ColumnInfo
    val lowerBound: Double,

    @ColumnInfo
    val startLookFrom: LocalDateTime,
)

@Dao
interface FavoriteDao {
    @Insert
    suspend fun insert(favorite: Favorite)

    @Delete
    suspend fun delete(favorite: Favorite)

    @Update
    suspend fun update(favorite: Favorite)

    @Query("SELECT * FROM Favorite")
    suspend fun getAllFavorites(): List<Favorite>
}

class FavoriteConverter {
    @TypeConverter
    fun dateTimeToTimestamp(dateTime: LocalDateTime) =
        dateTime.toEpochSecond(OffsetDateTime.now().offset)

    @TypeConverter
    fun dateTimeFromTimeStamp(timestamp: Long): LocalDateTime =
        LocalDateTime.ofEpochSecond(timestamp, 0, OffsetDateTime.now().offset)
}

class RateTypeConverter {
    @TypeConverter
    fun rateToInt(type: RateType) = type.ordinal

    @TypeConverter
    fun rateAtOrdinal(ordinal: Int) = RateType.values()[ordinal]
}
