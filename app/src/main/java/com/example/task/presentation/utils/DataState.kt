package com.example.task.presentation.utils

sealed class DataState<out T> {
    data class Success<out T : Any?>(val data: T) : DataState<T>()
    data class Error(val error: String) : DataState<Nothing>()
}

sealed class UIState<out T> {
    data object Loading : UIState<Nothing>()
    data class Success<out T : Any?>(val data: T) : UIState<T>()
    data class Error<out T : Any?>(val data: T) : UIState<T>()
}






