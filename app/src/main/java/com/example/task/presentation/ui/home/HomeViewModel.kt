package com.example.task.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.data.api.model.Cat
import com.example.task.domain.model.CatUIModel
import com.example.task.domain.usecases.HomeUseCases
import com.example.task.presentation.utils.DataState
import com.example.task.presentation.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases,
) : ViewModel() {
    private val _state = MutableStateFlow<UIState<CatUIModel>>(UIState.Loading)

    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UIState.Loading,
    )

    fun onEvent(intent: HomeScreenIntent) {
        when (intent) {
            HomeScreenIntent.GetHomeData -> {
                getHomePage()
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getHomePage() {

        viewModelScope.launch(Dispatchers.IO) {
            val cats = (1..CATS_LIST_NUM).asFlow()
                .flatMapMerge {
                    flow {
                        homeUseCases.getCatUseCase.invoke().collect {
                            when (it) {
                                is DataState.Error -> emit(Cat())
                                is DataState.Success -> emit(it.data)
                            }
                        }
                    }
                }.toList()

            if (cats.any { it.url != null }) {
                _state.value = UIState.Success(CatUIModel(cats))
            } else {
                _state.value = UIState.Error(CatUIModel())
            }
        }
    }
}


