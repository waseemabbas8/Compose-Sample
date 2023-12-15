package com.waseem.sample.feature.medicine.domain

import com.waseem.sample.core.usecase.NoParams
import com.waseem.sample.core.usecase.UseCase
import com.waseem.sample.feature.medicine.domain.model.HealthProblem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface GetHealthProblems : UseCase<List<HealthProblem>, NoParams>

class GetHealthProblemsImpl(
    private val dispatcher: CoroutineDispatcher,
    private val medicineRepository: MedicineRepository
) : GetHealthProblems {
    override suspend fun invoke(params: NoParams): Result<List<HealthProblem>> {
        return withContext(dispatcher) {
            medicineRepository.getHealthProblems()
        }
    }
}