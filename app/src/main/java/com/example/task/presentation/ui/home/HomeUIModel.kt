package com.example.task.presentation.ui.home

import com.example.task.data.api.model.Cat


data class HomeUIModel(
    val cats: List<Cat> = emptyList()
)
