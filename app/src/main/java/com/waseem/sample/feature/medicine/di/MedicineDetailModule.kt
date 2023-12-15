package com.waseem.sample.feature.medicine.di

import com.waseem.sample.feature.medicine.domain.GetMedicineDetail
import com.waseem.sample.feature.medicine.domain.GetMedicineDetailImpl
import com.waseem.sample.feature.medicine.domain.MedicineRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher

@InstallIn(ViewModelComponent::class)
@Module
object MedicineDetailModule {

    @Provides
    fun provideGetMedicineDetail(
        dispatcher: CoroutineDispatcher,
        medicineRepository: MedicineRepository
    ): GetMedicineDetail {
        return GetMedicineDetailImpl(medicineRepository = medicineRepository, dispatcher = dispatcher)
    }

}