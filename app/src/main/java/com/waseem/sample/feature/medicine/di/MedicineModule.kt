package com.waseem.sample.feature.medicine.di

import com.waseem.sample.core.network.NetworkClient
import com.waseem.sample.feature.medicine.data.MedicineRepositoryImpl
import com.waseem.sample.feature.medicine.domain.GetHealthProblems
import com.waseem.sample.feature.medicine.domain.GetHealthProblemsImpl
import com.waseem.sample.feature.medicine.domain.MedicineRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MedicineModule {

    @Provides
    @Singleton
    fun provideMedicineRepository(
        networkClient: NetworkClient
    ): MedicineRepository {
        return MedicineRepositoryImpl(httpClient = networkClient)
    }

    @Provides
    fun provideGetHealthProblems(
        dispatcher: CoroutineDispatcher,
        medicineRepository: MedicineRepository
    ): GetHealthProblems {
        return GetHealthProblemsImpl(dispatcher = dispatcher, medicineRepository = medicineRepository)
    }

}