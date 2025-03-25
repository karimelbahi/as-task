package com.example.task.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.task.presentation.ui.home.HomeScreen

fun appRoute(
    navBuilder: NavGraphBuilder,
) {
    with(navBuilder) {
        composable<AppScreens.HomeScreen> {
            HomeScreen()
        }
    }

}
