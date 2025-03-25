package com.example.task.presentation.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.task.R
import com.example.task.common.components.ErrorDialog
import com.example.task.common.components.LoadingDialog
import com.example.task.common.components.MySpace4
import com.example.task.common.utils.TestTags.CATS_LIST
import com.example.task.common.utils.TestTags.CAT_ITEM
import com.example.task.common.utils.TestTags.REFRESH_BUTTON
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
            CatsListComponent(stateValue.data, onRefresh)
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
private fun CatsListComponent(
    homeData: CatUIModel,
    onRefresh: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MainMargin),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_refresh_24),
            contentDescription = "Thumb Up",
            modifier = Modifier
                .testTag(REFRESH_BUTTON)
                .noRippleClickable {
                    onRefresh.invoke()
                },
        )
        MySpace4()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag(CATS_LIST),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                homeData.cats.size,
                key = { homeData.cats[it].id },
                contentType = { _ -> CAT_ITEM }) {
                CatComponent(cat = homeData.cats[it])
            }
        }
    }
}

@Preview(name = "Home Preview", showBackground = true)
@Composable
private fun SuccessComponentPreview() {
    CatsListComponent(
        homeData = CatUIModel(cats = listOf()),
        onRefresh = { },
    )
}
