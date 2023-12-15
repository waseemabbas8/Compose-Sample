package com.waseem.sample.feature.auth.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.waseem.sample.R
import com.waseem.sample.core.compose.EditText
import com.waseem.sample.core.compose.FilledNetworkButton
import com.waseem.sample.core.mvi.collectState
import com.waseem.sample.core.ui.ThemedPreview
import com.waseem.sample.feature.auth.domain.User

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: (user: User) -> Unit
) {
    val state by viewModel.collectState()

    when(state) {
        is LoginState.SuccessState -> onLoginSuccess((state as LoginState.SuccessState).user)
        is LoginState.ErrorState -> {
            Toast.makeText(LocalContext.current, (state as LoginState.ErrorState).msg, Toast.LENGTH_SHORT).show()
        }
        else -> {}
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.onboarding_padding))
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "Login to your account",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.SemiBold
            ),
        )
        LoginForm(
            state = state,
            onLoginClick = { email, password ->
                viewModel.action(LoginAction.SignInClick(email, password))
            },
        )
    }
}

@Composable
private fun LoginForm(
    state: LoginState,
    onLoginClick: (email: String, password: String) -> Unit,
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    Spacer(modifier = Modifier.height(25.dp))
    EditText(
        text = email.value,
        hint = stringResource(R.string.hint_email),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        onValueChange = { newValue: String -> email.value = newValue },
    )
    Spacer(modifier = Modifier.height(16.dp))
    EditText(
        text = password.value,
        hint = stringResource(R.string.password),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        onValueChange = { newValue: String -> password.value = newValue },
    )
    val text = when(state) {
        is LoginState.SuccessState -> stringResource(id = R.string.login_success)
        is LoginState.ErrorState -> stringResource(id = R.string.retry_login)
        else -> stringResource(id = R.string.login)
    }
    FilledNetworkButton(
        text = text,
        loading = state == LoginState.LoadingState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 25.dp)
    ) {
        onLoginClick(email.value, password.value)
    }
}

@Composable
@Preview
fun LoginScreenPreview() {
    ThemedPreview {
        LoginScreen(viewModel = hiltViewModel()) {}
    }
}