package com.example.task.domain.model

import com.example.task.data.api.model.Cat

data class CatUIModel(
    val cats: List<Cat> = emptyList()
)