package com.waseem.sample.feature.home.presentation

import com.waseem.sample.core.mvi.MviAction
import com.waseem.sample.core.mvi.MviEvent
import com.waseem.sample.core.mvi.MviResult
import com.waseem.sample.core.mvi.MviStateReducer
import com.waseem.sample.core.mvi.MviViewState
import com.waseem.sample.feature.medicine.domain.model.HealthProblem
import javax.inject.Inject

sealed class HomeAction : MviAction {
    data object Load : HomeAction()
    data class MedicineClicked(val medicineName: String) : HomeAction()
}

sealed class HomeResult : MviResult {
    data object Loading: HomeResult()
    data class HomeContent(val healthProblems: List<HealthProblem>) : HomeResult()
    data object Failure : HomeResult()
}

sealed class HomeEvent : MviEvent, HomeResult() {
    data class GotoMedicineDetail(val medicineName: String) : HomeEvent()
}

sealed class HomeState : MviViewState {
    data object DefaultState : HomeState()
    data object LoadingState : HomeState()
    data class HomeContentState(val uiState: HomeUiState) : HomeState()
    data object ErrorState : HomeState()
}

class HomeReducer @Inject constructor() : MviStateReducer<HomeState, HomeResult> {
    override fun HomeState.reduce(result: HomeResult): HomeState {
        return when(val previousState = this) {
            is HomeState.DefaultState -> previousState + result
            is HomeState.ErrorState -> previousState + result
            is HomeState.HomeContentState -> previousState + result
            is HomeState.LoadingState -> previousState + result
        }
    }

    private operator fun HomeState.DefaultState.plus(result: HomeResult): HomeState {
        return when(result) {
            HomeResult.Loading -> HomeState.LoadingState
            else -> throw IllegalStateException("unsupported")
        }
    }

    private operator fun HomeState.LoadingState.plus(result: HomeResult): HomeState {
        return when(result) {
            HomeResult.Loading -> HomeState.LoadingState
            is HomeResult.HomeContent -> HomeState.HomeContentState(
                uiState = HomeUiState(
                    diseases = result.healthProblems.map { healthProblem -> healthProblem.toUiState() }
                )
            )
            HomeResult.Failure -> HomeState.ErrorState
            else -> throw IllegalStateException("unsupported")
        }
    }

    private operator fun HomeState.ErrorState.plus(result: HomeResult): HomeState {
        return when(result) {
            HomeResult.Loading -> HomeState.LoadingState
            else -> throw IllegalStateException("unsupported")
        }
    }

    private operator fun HomeState.HomeContentState.plus(result: HomeResult): HomeState {
        return when(result) {
            HomeResult.Loading -> HomeState.LoadingState
            is HomeResult.HomeContent -> HomeState.HomeContentState(
                uiState = HomeUiState(
                    diseases = result.healthProblems.map { healthProblem -> healthProblem.toUiState() }
                )
            )
            else -> throw IllegalStateException("unsupported")
        }
    }
}