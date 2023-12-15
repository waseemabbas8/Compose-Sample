package com.waseem.sample.feature.medicine.data.api

import kotlinx.serialization.Serializable

@Serializable
data class MedicalInfoResponse(
    val problems: List<HealthProblemDto>,
)


