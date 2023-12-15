package com.waseem.sample.feature.auth.domain.usecase

import com.waseem.sample.core.usecase.NoParams
import com.waseem.sample.core.usecase.ObservableUseCase
import com.waseem.sample.feature.auth.domain.AuthRepository
import com.waseem.sample.feature.auth.domain.AuthState
import kotlinx.coroutines.flow.Flow

interface ObserveAuthState : ObservableUseCase<AuthState, NoParams>

class ObserveAuthStateImpl(
    private val authRepository: AuthRepository
) : ObserveAuthState {
    override fun invoke(params: NoParams): Flow<AuthState> = authRepository.getAuthState()
}