package com.waseem.sample.feature.auth.domain

import kotlinx.coroutines.flow.Flow


interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<User>

    suspend fun deleteUser()

    suspend fun saveUser(user: User)

    suspend fun getUser(): Result<User>

    /***
    * This is a flow because we want to observe the user state.
     * return [AuthState.Unauthenticated] if the user is not authenticated
     */
    fun getAuthState(): Flow<AuthState>
}