package com.waseem.sample.feature.medicine.data.api

import com.waseem.sample.feature.medicine.domain.model.HealthProblem
import kotlinx.serialization.Serializable

@Serializable
data class HealthProblemDto(
    val name: String,
    val medications: List<MedicationDto>,
    val labs: List<LabTestDto>,
)

fun HealthProblemDto.toDomain() = HealthProblem(
    name = name,
    medications = medications.map { it.toDomain() },
    labs = labs.map { it.toDomain() }
)