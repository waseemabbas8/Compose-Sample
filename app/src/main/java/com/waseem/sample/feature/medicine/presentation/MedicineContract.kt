package com.waseem.sample.feature.medicine.presentation

import com.waseem.sample.core.mvi.MviAction
import com.waseem.sample.core.mvi.MviEvent
import com.waseem.sample.core.mvi.MviResult
import com.waseem.sample.core.mvi.MviStateReducer
import com.waseem.sample.core.mvi.MviViewState
import com.waseem.sample.feature.medicine.domain.model.MedicineDetail
import javax.inject.Inject

sealed class MedicineDetailAction : MviAction {
    data object Load : MedicineDetailAction()
}

sealed class MedicineDetailResult : MviResult {
    data object Loading : MedicineDetailResult()
    data class Success(val medicineDetail: MedicineDetail) : MedicineDetailResult()
    data class Failure(val error: Throwable) : MedicineDetailResult()
}

sealed class MedicineDetailEvent : MviEvent, MedicineDetailResult()

sealed class MedicineDetailState : MviViewState {
    data object Default : MedicineDetailState()
    data object Loading : MedicineDetailState()
    data class Success(val uiState: MedicineUiState) : MedicineDetailState()
    data class Error(val error: Throwable) : MedicineDetailState()
}

class MedicineDetailReducer @Inject constructor() : MviStateReducer<MedicineDetailState, MedicineDetailResult> {
    override fun MedicineDetailState.reduce(result: MedicineDetailResult): MedicineDetailState {
        return when (val previousState = this) {
            is MedicineDetailState.Default -> previousState + result
            is MedicineDetailState.Error -> previousState + result
            is MedicineDetailState.Loading -> previousState + result
            is MedicineDetailState.Success -> previousState + result
        }
    }

    private operator fun MedicineDetailState.Default.plus(result: MedicineDetailResult): MedicineDetailState {
        return when (result) {
            MedicineDetailResult.Loading -> MedicineDetailState.Loading
            else -> throw IllegalStateException("unsupported result $result")
        }
    }

    private operator fun MedicineDetailState.Loading.plus(result: MedicineDetailResult): MedicineDetailState {
        return when (result) {
            MedicineDetailResult.Loading -> MedicineDetailState.Loading
            is MedicineDetailResult.Success -> MedicineDetailState.Success(
                uiState = result.medicineDetail.toUiState()
            )

            is MedicineDetailResult.Failure -> MedicineDetailState.Error(
                error = result.error
            )
        }
    }

    private operator fun MedicineDetailState.Success.plus(result: MedicineDetailResult): MedicineDetailState {
        return when (result) {
            MedicineDetailResult.Loading -> MedicineDetailState.Loading
            else -> throw IllegalStateException("unsupported result $result")
        }
    }

    private operator fun MedicineDetailState.Error.plus(result: MedicineDetailResult): MedicineDetailState {
        return when (result) {
            MedicineDetailResult.Loading -> MedicineDetailState.Loading
            else -> throw IllegalStateException("unsupported result $result")
        }
    }
}