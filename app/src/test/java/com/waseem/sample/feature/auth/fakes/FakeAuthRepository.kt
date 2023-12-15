package com.waseem.sample.feature.auth.fakes

import com.waseem.sample.feature.auth.domain.AuthRepository
import com.waseem.sample.feature.auth.domain.AuthState
import com.waseem.sample.feature.auth.domain.User
import com.waseem.sample.feature.auth.fakes.data.FakeUserList
import kotlinx.coroutines.flow.Flow

class FakeAuthRepository : AuthRepository {
    override suspend fun signIn(email: String, password: String): Result<User> {
        val users = FakeUserList()
        val user = users.firstOrNull { it.email == email }
        return if (user != null) {
            Result.success(user)
        } else {
            Result.failure(Exception("User not found"))
        }
    }

    override suspend fun deleteUser() {
        TODO("Not yet implemented")
    }

    override suspend fun saveUser(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(): Result<User> {
        TODO("Not yet implemented")
    }

    override fun getAuthState(): Flow<AuthState> {
        TODO("Not yet implemented")
    }
}