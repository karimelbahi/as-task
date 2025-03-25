package com.example.task.data.repo

import app.cash.turbine.test
import com.example.task.data.api.retrofit.WebService
import com.example.task.data.api.utils.ErrorEntity
import com.example.task.data.api.utils.GeneralErrorHandler
import com.example.task.presentation.utils.DataState
import com.example.task.presentation.utils.NetworkUtils
import com.example.task.utils.BAD_REQUEST
import com.example.task.utils.MSG_NETWORK_ERROR
import com.example.task.utils.MSG_NOT_FOUND_ERROR
import com.example.task.utils.MSG_SERVICE_UNAVAILABLE
import com.example.task.utils.MSG_UNKNOWN_ERROR
import com.example.task.utils.MainDispatcherRule
import com.example.task.utils.NO_INTERNET_CONNECTION
import com.example.task.utils.fakeCat
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.every
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class CatRepoTest {
    @get:Rule(order = 0)
    val mockkRule = MockKRule(this)

    @get:Rule(order = 1)
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var sut: CatsRepoImpl
    private val webService: WebService = mockk()
    private val errorHandler: GeneralErrorHandler = mockk()
    private val networkUtils: NetworkUtils = mockk()

    @Before
    fun setUp() {
        sut = CatsRepoImpl(webService, errorHandler, networkUtils)
    }

    @Test
    fun `getCats when network available returns success`() =
        runTest {
            // Given
            every { networkUtils.isNetworkAvailable() } returns true
            coEvery { webService.getCats() } returns listOf(fakeCat)

            // When
            val result = sut.getCats()

            // Then
            result.test {
                val item = awaitItem()
                Truth.assertThat(item).isEqualTo(DataState.Success(fakeCat))
                awaitComplete()
            }
        }

    @Test
    fun `getCats when network not available returns error`() = runTest {
        // Given
        every { networkUtils.isNetworkAvailable() } returns false
        every { errorHandler.getErrorMessage(ErrorEntity.Network) } returns NO_INTERNET_CONNECTION

        // When
        val result = sut.getCats()

        // Then
        result.test {
            val item = awaitItem()
            Truth.assertThat(item).isEqualTo(DataState.Error(NO_INTERNET_CONNECTION))
            awaitComplete()
        }
    }

    @Test
    fun `getCats when api throws exception returns error`() = runTest {
        // Given
        every { networkUtils.isNetworkAvailable() } returns true
        coEvery { webService.getCats() } throws Exception()
        every { errorHandler.getError(any()) } returns ErrorEntity.Unknown
        every { errorHandler.getErrorMessage(ErrorEntity.Unknown) } returns MSG_UNKNOWN_ERROR

        // When
        val result = sut.getCats()

        // Then
        result.test {
            val item = awaitItem()
            Truth.assertThat(item).isEqualTo(DataState.Error(MSG_UNKNOWN_ERROR))
            awaitComplete()
        }
    }

    @Test
    fun `getCats when network available but empty list returns error`() = runTest {
        // Given
        every { networkUtils.isNetworkAvailable() } returns true
        coEvery { webService.getCats() } returns emptyList()
        every { errorHandler.getError(any()) } returns ErrorEntity.NotFound
        every { errorHandler.getErrorMessage(ErrorEntity.NotFound) } returns MSG_NOT_FOUND_ERROR

        // When
        val result = sut.getCats()

        // Then
        result.test {
            val item = awaitItem()
            Truth.assertThat(item).isEqualTo(DataState.Error(MSG_NOT_FOUND_ERROR))
            awaitComplete()
        }
    }

    @Test
    fun `getCats when HttpException with 404 returns NotFound error`() = runTest {
        // Given
        every { networkUtils.isNetworkAvailable() } returns true
        coEvery { webService.getCats() } throws HttpException(
            Response.error<Any>(404, ResponseBody.create(null, ""))
        )
        every { errorHandler.getError(any()) } returns ErrorEntity.NotFound
        every { errorHandler.getErrorMessage(ErrorEntity.NotFound) } returns MSG_NOT_FOUND_ERROR

        // When
        val result = sut.getCats()

        // Then
        result.test {
            val item = awaitItem()
            Truth.assertThat(item).isEqualTo(DataState.Error(MSG_NOT_FOUND_ERROR))
            awaitComplete()
        }
    }


    @Test
    fun `getCats when HttpException with 503 returns ServiceUnavailable error`() = runTest {
        // Given
        every { networkUtils.isNetworkAvailable() } returns true
        coEvery { webService.getCats() } throws HttpException(
            Response.error<Any>(503, ResponseBody.create(null, ""))
        )
        every { errorHandler.getError(any()) } returns ErrorEntity.ServiceUnavailable
        every { errorHandler.getErrorMessage(ErrorEntity.ServiceUnavailable) } returns MSG_SERVICE_UNAVAILABLE

        // When
        val result = sut.getCats()

        // Then
        result.test {
            val item = awaitItem()
            Truth.assertThat(item).isEqualTo(DataState.Error(MSG_SERVICE_UNAVAILABLE))
            awaitComplete()
        }
    }

    @Test
    fun `getCats when IOException occurs returns Network error`() = runTest {
        // Given
        every { networkUtils.isNetworkAvailable() } returns true
        coEvery { webService.getCats() } throws IOException("Network error")
        every { errorHandler.getError(any()) } returns ErrorEntity.Network
        every { errorHandler.getErrorMessage(ErrorEntity.Network) } returns MSG_NETWORK_ERROR

        // When
        val result = sut.getCats()

        // Then
        result.test {
            val item = awaitItem()
            Truth.assertThat(item).isEqualTo(DataState.Error(MSG_NETWORK_ERROR))
            awaitComplete()
        }
    }

    @Test
    fun `getCats when BadRequest occurs returns BadRequest error`() = runTest {
        // Given
        every { networkUtils.isNetworkAvailable() } returns true
        coEvery { webService.getCats() } throws HttpException(
            Response.error<Any>(400, ResponseBody.create(null, ""))
        )
        every { errorHandler.getError(any()) } returns ErrorEntity.BadRequest
        every { errorHandler.getErrorMessage(ErrorEntity.BadRequest) } returns BAD_REQUEST

        // When
        val result = sut.getCats()

        // Then
        result.test {
            val item = awaitItem()
            Truth.assertThat(item).isEqualTo(DataState.Error(BAD_REQUEST))
            awaitComplete()
        }
    }
}
