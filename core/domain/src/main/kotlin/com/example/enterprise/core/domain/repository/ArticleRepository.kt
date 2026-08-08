package com.example.enterprise.core.domain.repository

import com.example.enterprise.core.model.Article
import kotlinx.coroutines.flow.Flow

/** 领域层拥有仓库契约，数据层提供实现；依赖方向因此由实现指向抽象。 */
interface ArticleRepository {
    fun observeArticles(): Flow<List<Article>>
    suspend fun refresh(): Result<Unit>
}

