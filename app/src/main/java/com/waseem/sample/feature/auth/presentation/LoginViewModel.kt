package com.waseem.sample.feature.auth.presentation

import com.waseem.sample.core.BaseStateViewModel
import com.waseem.sample.feature.auth.domain.usecase.SignInWithEmailPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInWithEmailPassword: SignInWithEmailPassword,
    reducer: LoginReducer
) : BaseStateViewModel<LoginAction, LoginResult, LoginEvent, LoginState, LoginReducer>(
    initialState = LoginState.DefaultState,
    reducer = reducer
){
    override fun LoginAction.process(): Flow<LoginResult> {
        return when(this) {
            is LoginAction.SignInClick -> {
                flow {
                    signInWithEmailPassword(
                        params = SignInWithEmailPassword.Params(
                            email = email,
                            password = password
                        )
                    ).onSuccess {
                        emit(LoginResult.Success(it))
                    }.onFailure {
                        emit(LoginResult.Failure(msg = it.message ?: "Something went wrong"))
                    }
                }.onStart {
                    emit(LoginResult.Loading)
                }.catch {
                    emit(LoginResult.Failure(msg = it.message ?: "Something went wrong"))
                }
            }
        }
    }
}