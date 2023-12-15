package com.waseem.sample.feature.home.presentation

import com.waseem.sample.feature.medicine.domain.model.HealthProblem
import com.waseem.sample.feature.medicine.domain.model.Medication

data class HomeUiState(
    val diseases: List<DiseaseItemUiState> = emptyList()
)

data class HomeHeaderUiState(
    val greetingMessage: String,
    val userEmail: String
)

data class DiseaseItemUiState(
    val name: String,
    val medicines: List<MedicineItemUiState>
)

fun HealthProblem.toUiState() = DiseaseItemUiState(
    name = name,
    medicines = medications.toUiState()
)

data class MedicineItemUiState(
    val name: String,
    val dose: String,
    val strength: String,
    val category: String,
)

fun List<Medication>.toUiState(): List<MedicineItemUiState> {
    val medicines = mutableListOf<MedicineItemUiState>()
    forEach {
        for (drug in it.drugs) {
            medicines.add(
                MedicineItemUiState(
                    name = drug.name,
                    dose = drug.dose,
                    strength = drug.strength,
                    category = it.name
                )
            )
        }
    }
    return medicines
}