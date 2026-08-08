package com.example.enterprise.core.database

import com.example.enterprise.core.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class InMemoryArticleLocalDataSource : ArticleLocalDataSource {
    private val articles = MutableStateFlow<List<Article>>(emptyList())

    override fun observeArticles(): Flow<List<Article>> = articles

    override suspend fun replaceAll(articles: List<Article>) {
        this.articles.value = articles.toList()
    }
}

