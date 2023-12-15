package com.waseem.sample.feature.medicine.presentation

import com.waseem.sample.feature.medicine.domain.model.MedicineDetail

data class MedicineUiState(
    val name: String,
    val dose: String,
    val strength: String,
    val category: String,
    val useFor: String
)

fun MedicineDetail.toUiState() = MedicineUiState(
    name = name,
    dose = dose,
    strength = strength,
    category = category,
    useFor = useFor
)