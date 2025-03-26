package com.example.task.ui.home

import android.util.Log
import app.cash.turbine.test
import com.example.task.data.repo.CatsRepoImpl
import com.example.task.domain.usecases.GetCatUseCase
import com.example.task.domain.usecases.HomeUseCases
import com.example.task.presentation.ui.home.HomeScreenIntent
import com.example.task.presentation.ui.home.HomeViewModel
import com.example.task.presentation.utils.DataState
import com.example.task.utils.MainDispatcherRule
import com.example.task.utils.fakeCats
import com.example.task.utils.fakeCat
import com.example.task.utils.toCatUIModel
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class HomeViewModelTest {
    @get:Rule(order = 0)
    val mockkRule = MockKRule(this)

    @get:Rule(order = 1)
    val mainDispatcherRule = MainDispatcherRule()
    private lateinit var sut: HomeViewModel
    private val homeUseCases: HomeUseCases = mockk()
    private var catsRepoImpl: CatsRepoImpl = mockk()

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.isLoggable(any(), any()) } returns false
        coEvery {
            homeUseCases.getCatUseCase
        } returns GetCatUseCase(catsRepoImpl)
        sut = HomeViewModel(homeUseCases)
    }


    @Test
    fun `test initial state`(): Unit = runTest {
        sut.state.test {
            val item = awaitItem()
            assertThat(item).isEqualTo(DataState.Loading)
        }
    }

    @Test
    fun `test calling onEvent GetCategoryMeals -  state is Success`(): Unit = runTest {

        sut.state.test {
            assertThat(awaitItem()).isEqualTo(DataState.Loading)
            coEvery {
                homeUseCases.getCatUseCase()
            } returns flow {
                emit(DataState.Success(fakeCat))
            }
            sut.onEvent(HomeScreenIntent.GetHomeData)
            assertThat(awaitItem()).isEqualTo(DataState.Success(fakeCats.toCatUIModel()))
        }
    }

    @Test
    fun `test calling onEvent GetCategoryMeals -  state is Failed`(): Unit = runTest {
        sut.state.test {
            assertThat(awaitItem()).isEqualTo(DataState.Loading)
            coEvery {
                homeUseCases.getCatUseCase()
            } returns flow {
                emit(DataState.Error("Error"))
            }
            sut.onEvent(HomeScreenIntent.GetHomeData)
            assertThat(awaitItem()).isEqualTo(DataState.Error("Error"))
        }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}
