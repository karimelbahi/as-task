package com.example.task.di

import com.example.task.presentation.utils.ResourcesResolver
import com.example.task.presentation.utils.ResourcesResolverImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun provideResourcesResolver(
        resourcesResolverImpl: ResourcesResolverImpl
    ): ResourcesResolver

}