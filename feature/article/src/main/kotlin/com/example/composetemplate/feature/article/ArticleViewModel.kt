package com.example.composetemplate.feature.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetemplate.core.domain.ObserveArticleUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ArticleViewModel(
    articleId: String,
    observeArticle: ObserveArticleUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ArticleUiState())
    val uiState = _uiState.asStateFlow()
    private val _effects = MutableSharedFlow<ArticleEffect>(extraBufferCapacity = 1)
    val effects = _effects.asSharedFlow()

    init {
        viewModelScope.launch {
            observeArticle(articleId).collect { article ->
                _uiState.value = ArticleUiState(isLoading = false, article = article)
            }
        }
    }

    fun onEvent(event: ArticleEvent) {
        when (event) {
            ArticleEvent.BackClicked -> _effects.tryEmit(ArticleEffect.GoBack)
        }
    }
}

