package com.waseem.sample.feature.auth

import com.waseem.sample.feature.auth.domain.AuthRepository
import com.waseem.sample.feature.auth.domain.usecase.SignInWithEmailPassword
import com.waseem.sample.feature.auth.domain.usecase.SignInWithEmailPasswordImpl
import com.waseem.sample.feature.auth.fakes.FakeAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class AuthModuleTest {

    private var authRepository: AuthRepository? = null
    private var signInWithEmailPassword: SignInWithEmailPassword? = null

    @Before
    fun setUp() {
        authRepository = FakeAuthRepository()
        signInWithEmailPassword = SignInWithEmailPasswordImpl(
            dispatcher = Dispatchers.IO,
            authRepository = authRepository!!
        )
    }

    @After
    fun tearDown() {
        authRepository = null
        signInWithEmailPassword = null
    }

    @Test
    fun `test successful login with email and password`() = runBlocking {
        val result = signInWithEmailPassword!!(
            SignInWithEmailPassword.Params(
                email = "your@example.com", password = "1234"
            )
        )
        assert(result.isSuccess)
    }

    @Test
    fun `test failed login with email and password`() = runBlocking {
        val result = signInWithEmailPassword!!(
            SignInWithEmailPassword.Params(
                email = "my@example.com", password = "1234"
            )
        )
        assert(result.isFailure)
    }
}