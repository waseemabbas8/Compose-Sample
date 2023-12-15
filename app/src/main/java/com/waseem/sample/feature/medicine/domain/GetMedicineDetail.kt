package com.waseem.sample.feature.medicine.domain

import com.waseem.sample.core.usecase.UseCase
import com.waseem.sample.feature.medicine.domain.model.MedicineDetail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


interface GetMedicineDetail : UseCase<MedicineDetail, GetMedicineDetail.Params> {
  data class Params(val drugName: String)
}

class GetMedicineDetailImpl(
    private val medicineRepository: MedicineRepository,
    private val dispatcher: CoroutineDispatcher,
) : GetMedicineDetail {
  override suspend fun invoke(params: GetMedicineDetail.Params): Result<MedicineDetail> {
      return withContext(dispatcher) {
          medicineRepository.getDrugDetails(drugName = params.drugName)
      }
  }
}