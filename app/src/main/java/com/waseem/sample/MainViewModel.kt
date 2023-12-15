package com.waseem.sample

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waseem.sample.core.usecase.NoParams
import com.waseem.sample.feature.auth.domain.AuthState
import com.waseem.sample.feature.auth.domain.usecase.ObserveAuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val observeAuthState: ObserveAuthState,
) : ViewModel() {

    private val _authState = mutableStateOf<AuthState>(AuthState.Unknown)
    val authState: State<AuthState> = _authState

    init {
        viewModelScope.launch {
            observeAuthState(NoParams).collect {
                Log.d("MainViewModel", "AuthState: $it")
                _authState.value = it
            }
        }
    }

}