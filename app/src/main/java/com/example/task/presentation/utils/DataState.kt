package com.example.task.presentation.utils

sealed class DataState<out T> {
    data object Loading : DataState<Nothing>()
    data class Success<out T : Any?>(val data: T) : DataState<T>()
    data class Error(val error: String) : DataState<Nothing>()
}
