package com.waseem.sample.feature.auth.di

import com.waseem.sample.core.AppDatabase
import com.waseem.sample.feature.auth.data.AuthRepositoryImpl
import com.waseem.sample.feature.auth.domain.AuthRepository
import com.waseem.sample.feature.auth.domain.usecase.ObserveAuthState
import com.waseem.sample.feature.auth.domain.usecase.ObserveAuthStateImpl
import com.waseem.sample.feature.auth.domain.usecase.SignInWithEmailPassword
import com.waseem.sample.feature.auth.domain.usecase.SignInWithEmailPasswordImpl
import com.waseem.sample.feature.auth.domain.usecase.SignOut
import com.waseem.sample.feature.auth.domain.usecase.SignOutImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher

@InstallIn(ViewModelComponent::class)
@Module
object AuthModule {

    @Provides
    fun provideAuthRepository(database: AppDatabase): AuthRepository {
        return AuthRepositoryImpl(database.userDao())
    }

    @Provides
    fun provideSignInWithEmailPassword(
        dispatcher: CoroutineDispatcher,
        authRepository: AuthRepository
    ): SignInWithEmailPassword {
        return SignInWithEmailPasswordImpl(dispatcher = dispatcher, authRepository = authRepository)
    }

    @Provides
    fun provideObserveAuthState(
        authRepository: AuthRepository
    ): ObserveAuthState {
        return ObserveAuthStateImpl(authRepository = authRepository)
    }

    @Provides
    fun provideSignOut(
        dispatcher: CoroutineDispatcher,
        authRepository: AuthRepository
    ): SignOut {
        return SignOutImpl(dispatcher = dispatcher, authRepository = authRepository)
    }

}