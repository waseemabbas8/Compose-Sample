package com.waseem.sample.feature.medicine.data.api

import com.waseem.sample.feature.medicine.domain.model.LabTest
import kotlinx.serialization.Serializable

@Serializable
data class LabTestDto(
    val name: String
)

fun LabTestDto.toDomain() = LabTest(
    name = name
)