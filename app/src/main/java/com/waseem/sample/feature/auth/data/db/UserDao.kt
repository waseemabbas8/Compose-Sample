package com.waseem.sample.feature.auth.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: UserEntity)

    @Query("DELETE FROM user")
    suspend fun deleteAll()

    @Query("SELECT * FROM user")
    suspend fun getUsers(): List<UserEntity>

    @Query("SELECT * FROM user")
    fun getAll(): Flow<List<UserEntity>>
}