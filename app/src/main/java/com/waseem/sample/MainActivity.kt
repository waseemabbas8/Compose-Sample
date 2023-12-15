package com.waseem.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.waseem.sample.core.ui.theme.SampleTheme
import com.waseem.sample.feature.auth.domain.AuthState
import com.waseem.sample.feature.auth.presentation.LoginScreen
import com.waseem.sample.feature.home.presentation.HomeViewModel
import com.waseem.sample.navigation.AppNavGraph
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleTheme {
                Scaffold {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        Crossfade(targetState = viewModel.authState.value, label = "scene") { state ->
                            when(state) {
                                is AuthState.Authenticated -> {
                                    AppNavGraph(
                                        navController = rememberNavController(),
                                        user = state.user
                                    )
                                }
                                AuthState.Unauthenticated -> {
                                    LoginScreen(viewModel = hiltViewModel()) {

                                    }
                                }
                                AuthState.Unknown -> {}
                            }
                        }
                    }
                }
            }
        }
    }

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface HomeViewModelFactoryProvider {
        fun homeViewModelFactory(): HomeViewModel.Factory
    }
}