package com.waseem.sample.feature.medicine.domain.model

data class HealthProblem(
    val name: String,
    val medications: List<Medication>,
    val labs: List<LabTest>,
)