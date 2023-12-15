package com.waseem.sample.core.di

import android.content.Context
import androidx.room.Room
import com.waseem.sample.core.AppDatabase
import com.waseem.sample.core.network.NetworkClient
import com.waseem.sample.core.network.Server
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "com.waseem.sample.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNetworkClient(): NetworkClient {
        return NetworkClient(
            server = Server.Staging
        )
    }
}