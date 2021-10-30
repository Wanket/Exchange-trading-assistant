package ru.wanket.exchange_trading_assistant.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Database(entities = [Favorite::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}

@Module
@InstallIn(SingletonComponent::class)
class DateBaseModule {
    @Provides
    @Singleton
    fun dataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, DataBase::class.java, "eta-base").build()
}
