package com.waseem.sample.feature.medicine.data.api

import com.waseem.sample.feature.medicine.domain.model.Medication
import kotlinx.serialization.Serializable

@Serializable
data class MedicationDto(
    val name: String,
    val drugs: List<DrugDto>,
)

fun MedicationDto.toDomain() = Medication(
    name = name,
    drugs = drugs.map { it.toDomain() }
)