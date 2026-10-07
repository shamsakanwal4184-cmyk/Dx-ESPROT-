package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.AppDao
import com.example.data.local.entities.*

@Database(
    entities = [
        User::class,
        Tournament::class,
        Registration::class,
        Match::class,
        Result::class,
        Notification::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}
