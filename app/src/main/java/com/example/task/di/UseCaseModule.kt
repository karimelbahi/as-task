package com.example.task.di

import com.example.task.domain.repo.CatsRepo
import com.example.task.domain.usecases.GetCatUseCase
import com.example.task.domain.usecases.HomeUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideCatsUseCase(catsRepo: CatsRepo): HomeUseCases{
        return HomeUseCases(GetCatUseCase(catsRepo))
    }

}