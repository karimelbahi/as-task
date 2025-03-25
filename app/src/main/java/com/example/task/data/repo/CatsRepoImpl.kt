package com.example.task.data.repo

import com.example.task.data.api.model.Cat
import com.example.task.data.api.retrofit.WebService
import com.example.task.data.api.utils.ErrorEntity
import com.example.task.data.api.utils.GeneralErrorHandler
import com.example.task.domain.repo.CatsRepo
import com.example.task.presentation.utils.DataState
import com.example.task.presentation.utils.NetworkUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatsRepoImpl @Inject constructor(
    private val webService: WebService,
    private val errorHandler: GeneralErrorHandler,
    private val networkUtils: NetworkUtils
) :CatsRepo {
    override fun getCats(): Flow<DataState<Cat>> = flow {

        return@flow if (networkUtils.isNetworkAvailable()) {
            try {
                val result = webService.getCats()
                val cat = result[0]
                emit(DataState.Success(cat))
            } catch (e: Exception) {
                emit(DataState.Error(errorHandler.getErrorMessage(errorHandler.getError(e))))
            } catch (e: HttpException) {
                emit(DataState.Error(errorHandler.getErrorMessage(errorHandler.getError(e))))
            }
        } else {
            emit(DataState.Error(errorHandler.getErrorMessage(ErrorEntity.Network)))
        }
    }

}