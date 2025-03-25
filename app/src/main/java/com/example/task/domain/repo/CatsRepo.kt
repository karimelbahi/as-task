package com.example.task.domain.repo

import com.example.task.data.api.model.Cat
import com.example.task.presentation.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface CatsRepo {
    fun getCats(): Flow<DataState<Cat>>
}