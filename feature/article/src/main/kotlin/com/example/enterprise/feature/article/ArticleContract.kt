package com.example.enterprise.feature.article

import com.example.enterprise.core.model.Article

data class ArticleUiState(
    val isLoading: Boolean = true,
    val article: Article? = null,
)

sealed interface ArticleEvent {
    data object BackClicked : ArticleEvent
}

sealed interface ArticleEffect {
    data object GoBack : ArticleEffect
}

interface ArticleNavigator {
    fun goBack()
}

