package com.waseem.sample.feature.medicine.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.waseem.sample.R
import com.waseem.sample.core.mvi.collectState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineScreen(
    viewModel: MedicineViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.collectState()

    Column {
        CenterAlignedTopAppBar(
            title = { Text(text = "Summary") },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(imageVector = Icons.Rounded.KeyboardArrowLeft, contentDescription = "Go back")
                }
            }
        )
        when (state) {
            is MedicineDetailState.Default -> {}
            is MedicineDetailState.Loading -> {
                LinearProgressIndicator()
            }
            is MedicineDetailState.Success -> {
                val uiState = (state as MedicineDetailState.Success).uiState
                Column(
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.horizontal_screen_padding))
                ) {
                    Row(
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Text(text = uiState.name, style = MaterialTheme.typography.headlineSmall)
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = uiState.strength,
                            style = MaterialTheme.typography.titleMedium.copy(color = Color.Black.copy(alpha = 0.6f))
                        )
                    }
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = "drug category: ${uiState.category}", style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = "dose: ${uiState.dose}", style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = "use for: ${uiState.useFor}",
                    )
                }
            }
            is MedicineDetailState.Error -> {
                Text(text = "Error")
            }
        }
    }
}
