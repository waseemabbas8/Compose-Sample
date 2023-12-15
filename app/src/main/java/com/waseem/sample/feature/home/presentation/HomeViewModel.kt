package com.waseem.sample.feature.home.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.waseem.sample.core.BaseStateViewModel
import com.waseem.sample.core.usecase.NoParams
import com.waseem.sample.feature.auth.domain.User
import com.waseem.sample.feature.medicine.domain.GetHealthProblems
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HomeViewModel @AssistedInject constructor(
    private val getHealthProblems: GetHealthProblems,
    @Assisted private val user: User,
    reducer: HomeReducer,
) : BaseStateViewModel<HomeAction, HomeResult, HomeEvent, HomeState, HomeReducer>(
    initialState = HomeState.DefaultState,
    reducer = reducer
) {

    init {
        action(HomeAction.Load)
    }

    val headerUiState = HomeHeaderUiState(
        greetingMessage = "${
            when (Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time.hour) {
                in 0..11 -> "Good Morning"
                in 12..16 -> "Good Afternoon"
                else -> "Good Evening"
            }
        } ${user.name}",
        userEmail = user.email
    )

    override fun HomeAction.process(): Flow<HomeResult> {
        return when (this) {
            is HomeAction.Load -> {
                flow<HomeResult> {
                    getHealthProblems(NoParams).onSuccess {
                        emit(HomeResult.HomeContent(it))
                    }.onFailure {
                        throw it
                    }
                }.onStart {
                    emit(HomeResult.Loading)
                }.catch {
                    Log.e("HomeViewModel", "process: ", it)
                    emit(HomeResult.Failure)
                }
            }
            is HomeAction.MedicineClicked -> emitEvent(HomeEvent.GotoMedicineDetail(medicineName))
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(user: User): HomeViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            factory: Factory,
            user: User
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return factory.create(user) as T
            }
        }
    }

}
