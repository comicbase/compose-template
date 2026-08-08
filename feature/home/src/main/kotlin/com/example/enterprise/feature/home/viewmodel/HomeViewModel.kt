package com.example.enterprise.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.enterprise.core.domain.ObserveArticlesUseCase
import com.example.enterprise.core.domain.RefreshArticlesUseCase
import com.example.enterprise.feature.home.event.HomeEffect
import com.example.enterprise.feature.home.event.HomeEvent
import com.example.enterprise.feature.home.state.HomeUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val observeArticles: ObserveArticlesUseCase,
    private val refreshArticles: RefreshArticlesUseCase,
) : ViewModel() {
    private val query = MutableStateFlow("")
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<HomeEffect>(extraBufferCapacity = 1)
    val effects = _effects.asSharedFlow()

    init {
        observeFilteredArticles()
        refresh()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.Refresh -> refresh()
            is HomeEvent.QueryChanged -> {
                query.value = event.query
                _uiState.value = _uiState.value.copy(query = event.query)
            }
            is HomeEvent.ArticleClicked -> _effects.tryEmit(HomeEffect.OpenArticle(event.id))
            HomeEvent.ProfileClicked -> _effects.tryEmit(HomeEffect.OpenProfile)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeFilteredArticles() {
        viewModelScope.launch {
            query.flatMapLatest(observeArticles::invoke).collect { articles ->
                _uiState.value = _uiState.value.copy(articles = articles)
            }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            refreshArticles().fold(
                onSuccess = { _uiState.value = _uiState.value.copy(isLoading = false) },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "数据加载失败，请重试",
                    )
                },
            )
        }
    }
}

