package com.example.task.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.data.api.model.Cat
import com.example.task.domain.model.CatUIModel
import com.example.task.domain.usecases.HomeUseCases
import com.example.task.presentation.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

const val CATS_LIST_NUM = 10
const val REQUEST_DELAY = 100L

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases,
) : ViewModel() {
    private val _state = MutableStateFlow<DataState<CatUIModel>>(DataState.Loading)

    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = DataState.Loading,
    )

    fun onEvent(intent: HomeScreenIntent) {
        when (intent) {
            HomeScreenIntent.GetHomeData -> {
                getCats()
            }

            HomeScreenIntent.OnReFetch -> {
                _state.value = DataState.Loading
                getCats()
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getCats() {
        viewModelScope.launch(Dispatchers.IO) {
            val cats = (1..CATS_LIST_NUM).asFlow()
                .flatMapMerge(concurrency = 3) {
                    flow {
                        delay(REQUEST_DELAY)
                        homeUseCases.getCatUseCase.invoke().collect {
                            when (it) {
                                is DataState.Error -> {emit(it)}
                                is DataState.Success -> {emit(it)}
                                DataState.Loading -> {}
                            }
                        }
                    }
                }.toList()

            if (cats.any { it is DataState.Error }) {
                when (val error = cats[0]) {
                    is DataState.Error -> {
                        _state.value = DataState.Error(error.error)
                    }
                    is DataState.Success -> {}
                    DataState.Loading -> {}
                }

            } else {
                _state.value = DataState.Success(CatUIModel(cats.extractCats()))
            }
        }
    }

    private fun List<DataState<Cat>>.extractCats(): List<Cat> {
        return this.mapNotNull { dataState ->
            when (dataState) {
                is DataState.Success -> dataState.data
                is DataState.Error -> null
                DataState.Loading -> null
            }
        }
    }
}


