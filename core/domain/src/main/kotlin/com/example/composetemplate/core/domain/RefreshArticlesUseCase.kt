package com.example.composetemplate.core.domain

import com.example.composetemplate.core.domain.repository.ArticleRepository

class RefreshArticlesUseCase(private val repository: ArticleRepository) {
    suspend operator fun invoke(): Result<Unit> = repository.refresh()
}
