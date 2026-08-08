package com.example.enterprise.core.domain

import com.example.enterprise.core.domain.repository.ArticleRepository

class RefreshArticlesUseCase(private val repository: ArticleRepository) {
    suspend operator fun invoke(): Result<Unit> = repository.refresh()
}
