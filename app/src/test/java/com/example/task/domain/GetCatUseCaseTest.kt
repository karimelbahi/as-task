package com.example.task.domain

import app.cash.turbine.test
import com.example.task.data.api.model.Cat
import com.example.task.data.repo.CatsRepoImpl
import com.example.task.domain.usecases.GetCatUseCase
import com.example.task.presentation.utils.DataState
import com.example.task.utils.CUSTOM_ERROR_MESSAGE
import com.example.task.utils.ERROR_MESSAGE
import com.example.task.utils.fakeCat
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetCatUseCaseTest {

    @get:Rule(order = 0)
    val mockkRule = MockKRule(this)

    private lateinit var sut: GetCatUseCase

    private var catsRepo: CatsRepoImpl = mockk()


    @Before
    fun setUp() {
        sut = GetCatUseCase(catsRepo)
    }

    @Test
    fun `invoke returns success with cat data when repository succeeds`() = runTest {
        // Given
        every { catsRepo.getCats() } returns flow {
            emit(DataState.Success(fakeCat))
        }

        // When
        val result = sut.invoke()

        // Then
        result.test {
            val item = awaitItem()
            assertThat(item).isInstanceOf(DataState.Success::class.java)
            assertThat((item as DataState.Success<Cat>).data).isEqualTo(fakeCat)
            awaitComplete()
        }
    }

    @Test
    fun `invoke returns error when repository fails`() = runTest {
        // Given
        every { catsRepo.getCats() } returns flow {
            emit(DataState.Error(ERROR_MESSAGE))
        }

        // When
        val result = sut.invoke()

        // Then
        result.test {
            val item = awaitItem()
            assertThat(item).isInstanceOf(DataState.Error::class.java)
            assertThat((item as DataState.Error).error).isEqualTo(ERROR_MESSAGE)
            awaitComplete()
        }
    }

    @Test
    fun `invoke preserves error message from repository`() = runTest {
        // Given
        every { catsRepo.getCats() } returns flow {
            emit(DataState.Error(CUSTOM_ERROR_MESSAGE))
        }

        // When
        val result = sut.invoke()

        // Then
        result.test {
            val item = awaitItem()
            assertThat(item).isInstanceOf(DataState.Error::class.java)
            assertThat((item as DataState.Error).error).isEqualTo(CUSTOM_ERROR_MESSAGE)
            awaitComplete()
        }
    }

    @Test
    fun `invoke returns success with correct cat properties`() = runTest {
        // Given

        every { catsRepo.getCats() } returns flow {
            emit(DataState.Success(fakeCat))
        }

        // When
        val result = sut.invoke()

        // Then
        result.test {
            val item = awaitItem()
            assertThat(item).isInstanceOf(DataState.Success::class.java)
            val cat = (item as DataState.Success<Cat>).data
            assertThat(cat.url).isEqualTo(fakeCat.url)
            assertThat(cat.id).isEqualTo(fakeCat.id)
            assertThat(cat.height).isEqualTo(fakeCat.height)
            assertThat(cat.width).isEqualTo(fakeCat.width)
            awaitComplete()
        }
    }

    @Test
    fun `invoke handles multiple emissions correctly`() = runTest {
        // Given
        every { catsRepo.getCats() } returns flow {
            emit(DataState.Success(fakeCat))
            emit(DataState.Error(ERROR_MESSAGE))
        }

        // When
        val result = sut.invoke()

        // Then
        result.test {
            val firstItem = awaitItem()
            assertThat(firstItem).isInstanceOf(DataState.Success::class.java)
            assertThat((firstItem as DataState.Success<Cat>).data).isEqualTo(fakeCat)

            val secondItem = awaitItem()
            assertThat(secondItem).isInstanceOf(DataState.Error::class.java)
            assertThat((secondItem as DataState.Error).error).isEqualTo(ERROR_MESSAGE)

            awaitComplete()
        }
    }

    @Test
    fun `invoke handles empty success response correctly`() = runTest {
        // Given
        val emptyCat = Cat(url = "", id = "", height = 0, width = 0)
        every { catsRepo.getCats() } returns flow {
            emit(DataState.Success(emptyCat))
        }

        // When
        val result = sut.invoke()

        // Then
        result.test {
            val item = awaitItem()
            assertThat(item).isInstanceOf(DataState.Success::class.java)
            val cat = (item as DataState.Success<Cat>).data
            assertThat(cat.url).isEmpty()
            assertThat(cat.id).isEmpty()
            assertThat(cat.height).isEqualTo(0)
            assertThat(cat.width).isEqualTo(0)
            awaitComplete()
        }
    }

    @Test
    fun `invoke handles empty error message correctly`() = runTest {
        // Given
        every { catsRepo.getCats() } returns flow {
            emit(DataState.Error(""))
        }

        // When
        val result = sut.invoke()

        // Then
        result.test {
            val item = awaitItem()
            assertThat(item).isInstanceOf(DataState.Error::class.java)
            assertThat((item as DataState.Error).error).isEmpty()
            awaitComplete()
        }
    }
}