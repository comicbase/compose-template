package com.example.enterprise.feature.home.event

sealed interface HomeEvent {
    data object Refresh : HomeEvent
    data class QueryChanged(val query: String) : HomeEvent
    data class ArticleClicked(val id: String) : HomeEvent
    data object ProfileClicked : HomeEvent
}

/** 一次性行为与可重放 UiState 分离，避免导航在重组时重复执行。 */
sealed interface HomeEffect {
    data class OpenArticle(val id: String) : HomeEffect
    data object OpenProfile : HomeEffect
}

