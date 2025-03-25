package com.example.task.presentation.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = hiltViewModel()
    val stateValue by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(HomeScreenIntent.GetHomeData)
    }
    HomeScreenContent(stateValue)
}