package com.example.task.di

import com.example.task.data.api.retrofit.WebService
import com.example.task.data.api.utils.GeneralErrorHandler
import com.example.task.data.repo.CatsRepoImpl
import com.example.task.domain.repo.CatsRepo
import com.example.task.presentation.utils.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCatsRepo(
        webService: WebService,
        errorHandler: GeneralErrorHandler,
        networkUtils: NetworkUtils
    ): CatsRepo {
        return CatsRepoImpl(
            webService,
            errorHandler,
            networkUtils
        )
    }

}

