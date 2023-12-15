package com.waseem.sample.feature.auth.domain

sealed class AuthState {
    data object Unauthenticated : AuthState()
    data class Authenticated(val user: User) : AuthState()
    data object Unknown : AuthState()
}