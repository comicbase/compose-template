package com.example.enterprise.feature.home.state

import com.example.enterprise.core.model.Article

/** Home 页唯一、不可变的状态快照。更新只能通过 ViewModel 内部 copy 产生。 */
data class HomeUiState(
    val isLoading: Boolean = true,
    val query: String = "",
    val articles: List<Article> = emptyList(),
    val errorMessage: String? = null,
) {
    val showEmpty: Boolean get() = !isLoading && errorMessage == null && articles.isEmpty()
}

