package com.waseem.sample.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.waseem.sample.feature.auth.data.db.UserDao
import com.waseem.sample.feature.auth.data.db.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}