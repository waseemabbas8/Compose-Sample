package com.waseem.sample.feature.auth.domain.usecase

import com.waseem.sample.core.usecase.UseCase
import com.waseem.sample.feature.auth.domain.AuthRepository
import com.waseem.sample.feature.auth.domain.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface SignInWithEmailPassword : UseCase<User, SignInWithEmailPassword.Params> {
    data class Params(val email: String, val password: String)
}

class SignInWithEmailPasswordImpl(
    private val dispatcher: CoroutineDispatcher,
    private val authRepository: AuthRepository
) : SignInWithEmailPassword {
    override suspend fun invoke(params: SignInWithEmailPassword.Params): Result<User> {
        return withContext(dispatcher) {
            authRepository.signIn(params.email, params.password)
        }
    }
}