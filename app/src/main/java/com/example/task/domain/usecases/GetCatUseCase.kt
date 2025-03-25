package com.example.task.domain.usecases

import com.example.task.data.api.model.Cat
import com.example.task.domain.repo.CatsRepo
import com.example.task.presentation.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCatUseCase @Inject constructor (
    private val repository: CatsRepo,
) {
    operator fun invoke() : Flow<DataState<Cat>> {
      return repository.getCats()
    }
}
