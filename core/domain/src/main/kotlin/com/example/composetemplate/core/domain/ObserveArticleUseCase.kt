package com.example.composetemplate.core.domain

import com.example.composetemplate.core.domain.repository.ArticleRepository
import com.example.composetemplate.core.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveArticleUseCase(private val repository: ArticleRepository) {
    operator fun invoke(id: String): Flow<Article?> =
        repository.observeArticles().map { articles -> articles.firstOrNull { it.id == id } }
}
