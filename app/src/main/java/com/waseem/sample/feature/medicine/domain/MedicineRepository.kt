package com.waseem.sample.feature.medicine.domain

import com.waseem.sample.feature.medicine.domain.model.HealthProblem
import com.waseem.sample.feature.medicine.domain.model.MedicineDetail

interface MedicineRepository {
    suspend fun getHealthProblems(): Result<List<HealthProblem>>
    suspend fun getDrugDetails(drugName: String): Result<MedicineDetail>
}