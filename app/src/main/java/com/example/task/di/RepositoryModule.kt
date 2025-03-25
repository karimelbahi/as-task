package com.example.task.di

import com.example.task.data.repo.CatsRepoImpl
import com.example.task.domain.repo.CatsRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    // TODO:(karim)
    @Binds
    abstract fun getCatsRepo(repo: CatsRepoImpl): CatsRepo

}