package com.example.task.domain.usecases

import javax.inject.Inject

data class HomeUseCases @Inject constructor(
    val getCatUseCase: GetCatUseCase
)