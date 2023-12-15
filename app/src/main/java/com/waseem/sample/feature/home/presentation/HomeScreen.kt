package com.waseem.sample.feature.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.waseem.sample.R
import com.waseem.sample.core.compose.FilledButton
import com.waseem.sample.core.mvi.collectEvents
import com.waseem.sample.core.mvi.collectState

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onMedicineClick: (medicineName: String) -> Unit
) {
    viewModel.collectEvents {
        when(it) {
            is HomeEvent.GotoMedicineDetail -> onMedicineClick(it.medicineName)
        }
    }
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = dimensionResource(id = R.dimen.horizontal_screen_padding))
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.vertical_screen_padding)))
        Header(uiState = viewModel.headerUiState)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.vertical_screen_padding)))
        Body(viewModel = viewModel)
    }
}

@Composable
private fun Header(uiState: HomeHeaderUiState) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = uiState.greetingMessage,
                style = MaterialTheme.typography.titleLarge
            )
            Text(text = uiState.userEmail)
        }
    }
}

@Composable
private fun Body(viewModel: HomeViewModel) {
    val state by viewModel.collectState()
    when(state) {
        is HomeState.DefaultState -> {}
        HomeState.ErrorState -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                FilledButton(text = "Error! Retry") {
                    viewModel.action(HomeAction.Load)
                }
            }
        }
        is HomeState.HomeContentState -> {
            Content(
                uiState = (state as HomeState.HomeContentState).uiState,
                onMedicineClick = {
                    viewModel.action(HomeAction.MedicineClicked(it))
                }
            )
        }
        HomeState.LoadingState -> {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun Content(uiState: HomeUiState, onMedicineClick: (medicineName: String) -> Unit) {
    uiState.diseases.forEach { disease ->
        Text(text = disease.name, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(12.dp))
        disease.medicines.forEach { medicine ->
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .clickable {
                        onMedicineClick(medicine.name)
                    },
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color.White,
                ),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(text = medicine.name, style = MaterialTheme.typography.titleSmall)
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = medicine.strength, style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = "class: ${medicine.category}",
                    )
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                    ) {
                        Image(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_dose),
                            contentDescription = null
                        )
                        Text(modifier = Modifier.padding(start = 8.dp), text = medicine.dose)
                    }
                }
            }

        }
    }
}