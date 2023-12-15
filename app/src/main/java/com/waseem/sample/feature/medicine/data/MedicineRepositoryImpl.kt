package com.waseem.sample.feature.medicine.data

import com.waseem.sample.core.network.NetworkClient
import com.waseem.sample.feature.medicine.data.api.MedicalInfoResponse
import com.waseem.sample.feature.medicine.data.api.MedicineResource
import com.waseem.sample.feature.medicine.data.api.toDomain
import com.waseem.sample.feature.medicine.domain.MedicineRepository
import com.waseem.sample.feature.medicine.domain.model.HealthProblem
import com.waseem.sample.feature.medicine.domain.model.MedicineDetail

class MedicineRepositoryImpl(
    private val httpClient: NetworkClient
) : MedicineRepository {

    /***
     * This is a cache of the health problems. This is a simple way to cache the data.
     * In a real app, we would use a database to cache the data.
     */
    private var healthProblemsCache: List<HealthProblem> = emptyList()

    override suspend fun getHealthProblems(): Result<List<HealthProblem>> {
        return httpClient.get<MedicineResource, MedicalInfoResponse>(resource = MedicineResource())
            .map { response ->
                healthProblemsCache = response.problems.map { it.toDomain() }
                healthProblemsCache
            }
    }

    override suspend fun getDrugDetails(drugName: String): Result<MedicineDetail> {
        //this is just for demo purpose otherwise in production app we fetch these details from server
        healthProblemsCache.forEach {problem ->
            problem.medications.forEach {category ->
                category.drugs.forEach {
                    if (it.name == drugName) {
                        return Result.success(MedicineDetail(
                            name = it.name,
                            dose = it.dose,
                            strength = it.strength,
                            category = category.name,
                            useFor = problem.name
                        ))
                    }
                }
            }
        }
        return Result.failure(Exception("Drug not found"))
    }


}