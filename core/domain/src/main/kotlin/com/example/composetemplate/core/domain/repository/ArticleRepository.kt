package com.example.composetemplate.core.domain.repository

import com.example.composetemplate.core.model.Article
import kotlinx.coroutines.flow.Flow

/** 领域层拥有仓库契约，数据层提供实现；依赖方向因此由实现指向抽象。 */
interface ArticleRepository {
    fun observeArticles(): Flow<List<Article>>
    suspend fun refresh(): Result<Unit>
}

