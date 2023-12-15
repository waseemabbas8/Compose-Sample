package com.waseem.sample.feature.auth.data

import com.waseem.sample.feature.auth.data.db.UserDao
import com.waseem.sample.feature.auth.data.db.UserEntityMapper
import com.waseem.sample.feature.auth.data.db.toUser
import com.waseem.sample.feature.auth.domain.AuthRepository
import com.waseem.sample.feature.auth.domain.AuthState
import com.waseem.sample.feature.auth.domain.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.UUID

class AuthRepositoryImpl(
    private val userDao: UserDao
) : AuthRepository {
    override suspend fun signIn(email: String, password: String): Result<User> {
        //fake api call
        delay(1000)
        val dummyUser = User(id = UUID.randomUUID().toString(), email = email, name = "Waseem")
        saveUser(dummyUser)
        return Result.success(dummyUser)
    }

    override suspend fun deleteUser() {
        userDao.deleteAll()
    }

    override suspend fun saveUser(user: User) {
        userDao.insert(user = UserEntityMapper.fromUser(user))
    }

    override suspend fun getUser(): Result<User> {
        return Result.success(userDao.getUsers().first().toUser())
    }

    override fun getAuthState(): Flow<AuthState> {
        return userDao.getAll().map {
            if (it.isEmpty()) {
                AuthState.Unauthenticated
            } else {
                AuthState.Authenticated(it.first().toUser())
            }
        }
    }
}