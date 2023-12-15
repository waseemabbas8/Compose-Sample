package com.waseem.sample.feature.medicine.presentation

import androidx.lifecycle.SavedStateHandle
import com.waseem.sample.core.BaseStateViewModel
import com.waseem.sample.feature.medicine.domain.GetMedicineDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor(
    private val getMedicineDetail: GetMedicineDetail,
    savedStateHandle: SavedStateHandle,
    reducer: MedicineDetailReducer,
) : BaseStateViewModel<MedicineDetailAction, MedicineDetailResult, MedicineDetailEvent, MedicineDetailState, MedicineDetailReducer>(
    initialState = MedicineDetailState.Default,
    reducer = reducer
) {

    private val _drugName: String = checkNotNull(savedStateHandle["drugName"])

    init {
        action(MedicineDetailAction.Load)
    }

    override fun MedicineDetailAction.process(): Flow<MedicineDetailResult> {
        return when (this) {
            MedicineDetailAction.Load -> flow<MedicineDetailResult> {
                getMedicineDetail(
                    params = GetMedicineDetail.Params(
                        drugName = _drugName
                    )
                ).onSuccess {
                    emit(MedicineDetailResult.Success(it))
                }.onFailure {
                    throw it
                }
            }.onStart {
                emit(MedicineDetailResult.Loading)
            }.catch {
                emit(MedicineDetailResult.Failure(it))
            }
        }
    }

}
