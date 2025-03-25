package com.example.task.presentation.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.task.R
import com.example.task.common.components.ErrorDialog
import com.example.task.common.components.LoadingDialog
import com.example.task.common.utils.noRippleClickable
import com.example.task.domain.model.CatUIModel
import com.example.task.presentation.ui.home.components.CatComponent
import com.example.task.presentation.utils.UIState
import com.example.task.ui.theme.MainMargin

@Composable
fun HomeScreenContent(
    stateValue: UIState<CatUIModel>,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
) {
    when (stateValue) {
        UIState.Loading -> {
            LoadingDialog()
        }

        is UIState.Success -> {
            SuccessComponent(stateValue.data, onRefresh)
        }

        is UIState.Error -> {
            ErrorDialog(
                errorMessage = stateValue.error,
                onRetryClick = { onRetry.invoke() },
                onDismiss = {}
            )
        }
    }
}

@Composable
private fun SuccessComponent(
    homeData: CatUIModel,
    onRefresh: () -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(MainMargin),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_refresh_24),
            contentDescription = "Thumb Up",
            modifier =
            Modifier.noRippleClickable {
                onRefresh.invoke()
            },
        )

        for (cat in homeData.cats) {
            CatComponent(cat = cat)
        }
    }
}

@Preview(name = "Home Preview", showBackground = true)
@Composable
private fun SuccessComponentPreview() {
    SuccessComponent(
        homeData = CatUIModel(
            cats = listOf()
        ),
        onRefresh = { },
    )
}
