package com.example.task.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class AppScreens {
    @Serializable
    data object HomeScreen : AppScreens()
}