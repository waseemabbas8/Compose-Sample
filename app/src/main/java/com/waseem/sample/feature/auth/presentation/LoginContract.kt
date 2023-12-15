package com.waseem.sample.feature.auth.presentation

import com.waseem.sample.core.mvi.MviAction
import com.waseem.sample.core.mvi.MviEvent
import com.waseem.sample.core.mvi.MviResult
import com.waseem.sample.core.mvi.MviStateReducer
import com.waseem.sample.core.mvi.MviViewState
import com.waseem.sample.feature.auth.domain.User
import javax.inject.Inject

sealed class LoginAction : MviAction {
    data class SignInClick(val email: String, val password: String) : LoginAction()
}

sealed class LoginResult : MviResult {
    data object Loading : LoginResult()
    data class Success(val user: User) : LoginResult()
    data class Failure(val msg: String) : LoginResult()
}

sealed class LoginEvent : MviEvent, LoginResult()

sealed class LoginState : MviViewState {
    data object DefaultState : LoginState()
    data object LoadingState : LoginState()
    data class SuccessState(val user: User) : LoginState()
    data class ErrorState(val msg: String) : LoginState()
}

class LoginReducer @Inject constructor() : MviStateReducer<LoginState, LoginResult> {
    override fun LoginState.reduce(result: LoginResult): LoginState {
        return when (val previousState = this) {
            is LoginState.DefaultState -> previousState + result
            is LoginState.LoadingState -> previousState + result
            is LoginState.SuccessState -> previousState + result
            is LoginState.ErrorState -> previousState + result
        }
    }

    private operator fun LoginState.DefaultState.plus(result: LoginResult): LoginState {
        return when (result) {
            LoginResult.Loading -> LoginState.LoadingState
            else -> throw IllegalStateException("unsupported")
        }
    }

    private operator fun LoginState.LoadingState.plus(result: LoginResult): LoginState {
        return when (result) {
            is LoginResult.Success -> LoginState.SuccessState(user = result.user)
            is LoginResult.Failure -> LoginState.ErrorState(msg = result.msg)
            else -> throw IllegalStateException("unsupported")
        }
    }

    private operator fun LoginState.SuccessState.plus(result: LoginResult): LoginState {
        return when (result) {
            is LoginResult.Success -> LoginState.SuccessState(user = result.user)
            else -> throw IllegalStateException("unsupported")
        }
    }

    private operator fun LoginState.ErrorState.plus(result: LoginResult): LoginState {
        return when (result) {
            is LoginResult.Failure -> LoginState.ErrorState(msg = result.msg)
            else -> throw IllegalStateException("unsupported")
        }
    }
}