package com.waseem.sample.feature.auth.domain.usecase

import com.waseem.sample.core.usecase.NoParams
import com.waseem.sample.core.usecase.UseCase
import com.waseem.sample.feature.auth.domain.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface SignOut : UseCase<Unit, NoParams>

class SignOutImpl(
    private val dispatcher: CoroutineDispatcher,
    private val authRepository: AuthRepository
) : SignOut {
    override suspend fun invoke(params: NoParams): Result<Unit> {
        withContext(dispatcher) {
            authRepository.deleteUser()
        }
        return Result.success(Unit)
    }
}