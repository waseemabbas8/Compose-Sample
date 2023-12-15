package com.waseem.sample.feature.medicine.data.api

import com.waseem.sample.feature.medicine.domain.model.Drug
import kotlinx.serialization.Serializable

@Serializable
data class DrugDto(
    val dose: String,
    val name: String,
    val strength: String
)

fun DrugDto.toDomain() = Drug(
    dose = dose,
    name = name,
    strength = strength
)