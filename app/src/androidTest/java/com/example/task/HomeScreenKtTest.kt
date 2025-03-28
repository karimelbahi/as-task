package com.example.task

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.task.common.utils.TestTags.CATS_LIST
import com.example.task.common.utils.TestTags.CAT_ITEM
import com.example.task.common.utils.TestTags.LOADING_DIALOG
import com.example.task.common.utils.TestTags.REFRESH_BUTTON
import com.example.task.data.api.model.Cat
import com.example.task.domain.model.CatUIModel
import com.example.task.presentation.ui.home.HomeScreenContent
import com.example.task.presentation.utils.DataState
import org.junit.Rule
import org.junit.Test

val fakeCat = Cat(url = "https://example.com/same_cat.jpg", id = "1", height = 100, width = 150)
val fakeCats = List(10) { fakeCat }

internal class HomeScreenContentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenStateIsLoading_showsLoadingDialog() {
        // Given
        composeTestRule.setContent {
            HomeScreenContent(
                stateValue = DataState.Loading,
                onRefresh = {},
                onRetry = {}
            )
        }

        // Then
        composeTestRule
            .onNodeWithTag(LOADING_DIALOG)
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun whenStateIsError_showsErrorDialog() {
        // Given
        val errorMessage = "Test error message"

        composeTestRule.setContent {
            HomeScreenContent(
                stateValue = DataState.Error(errorMessage),
                onRefresh = {},
                onRetry = {}
            )
        }

        // Then
        composeTestRule
            .onNodeWithText(errorMessage)
            .assertExists()
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Retry")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun whenStateIsError_retryButtonClickable() {
        // Given
        var retryClicked = false

        composeTestRule.setContent {
            HomeScreenContent(
                stateValue = DataState.Error("Test error"),
                onRefresh = {},
                onRetry = { retryClicked = true }
            )
        }

        // When
        composeTestRule
            .onNodeWithText("Retry")
            .performClick()

        // Then
        assert(retryClicked)
    }

    @Test
    fun whenStateIsSuccess_showsCatsList() {
        // Given
        val catUIModel = CatUIModel(fakeCats)

        composeTestRule.setContent {
            HomeScreenContent(
                stateValue = DataState.Success(catUIModel),
                onRefresh = {},
                onRetry = {}
            )
        }

        // Then
        composeTestRule
            .onNodeWithTag(CATS_LIST)
            .assertExists()
            .assertIsDisplayed()

        // Verify refresh button is displayed
        composeTestRule
            .onNodeWithTag(REFRESH_BUTTON)
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun whenStateIsSuccess_refreshButtonClickable() {
        // Given
        var refreshClicked = false
        val catUIModel = CatUIModel(fakeCats)

        composeTestRule.setContent {
            HomeScreenContent(
                stateValue = DataState.Success(catUIModel),
                onRefresh = { refreshClicked = true },
                onRetry = {}
            )
        }

        // When
        composeTestRule
            .onNodeWithTag(REFRESH_BUTTON)
            .performClick()

        // Then
        assert(refreshClicked)
    }

    @Test
    fun whenStateIsSuccess_showsCorrectNumberOfCats() {
        // Given
        val numberOfCats = 3
        val catsList = List(numberOfCats) { fakeCat }
        val catUIModel = CatUIModel(catsList)

        composeTestRule.setContent {
            HomeScreenContent(
                stateValue = DataState.Success(catUIModel),
                onRefresh = {},
                onRetry = {}
            )
        }

        // Then
        composeTestRule
            .onAllNodesWithTag(CAT_ITEM)
            .assertCountEquals(numberOfCats)
    }

    @Test
    fun whenStateIsSuccess_catItemsDisplayCorrectInfo() {
        // Given
        val catUIModel = CatUIModel(listOf(fakeCat))

        composeTestRule.setContent {
            HomeScreenContent(
                stateValue = DataState.Success(catUIModel),
                onRefresh = {},
                onRetry = {}
            )
        }

        // Then
        composeTestRule
            .onNodeWithTag("cat_image_${fakeCat.id}")
            .assertExists()
            .assertIsDisplayed()
    }

}