package com.example.enterprise.core.database

import com.example.enterprise.core.model.Article
import kotlinx.coroutines.flow.Flow

/** 本地持久化边界。生产项目可替换为 Room 实现。 */
interface ArticleLocalDataSource {
    fun observeArticles(): Flow<List<Article>>
    suspend fun replaceAll(articles: List<Article>)
}

