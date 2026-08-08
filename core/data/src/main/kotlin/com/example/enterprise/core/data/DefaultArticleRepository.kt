package com.example.enterprise.core.data

import com.example.enterprise.core.common.DispatcherProvider
import com.example.enterprise.core.database.ArticleLocalDataSource
import com.example.enterprise.core.domain.repository.ArticleRepository
import com.example.enterprise.core.model.Article
import com.example.enterprise.core.network.ArticleRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * 单一数据源策略：UI 始终观察本地数据；刷新只负责把网络结果写入本地。
 * 这样网络与缓存不会分别驱动两套 UI 状态。
 */
class DefaultArticleRepository(
    private val remote: ArticleRemoteDataSource,
    private val local: ArticleLocalDataSource,
    private val dispatchers: DispatcherProvider,
) : ArticleRepository {
    override fun observeArticles(): Flow<List<Article>> = local.observeArticles()

    override suspend fun refresh(): Result<Unit> = withContext(dispatchers.io) {
        runCatching { local.replaceAll(remote.fetchArticles()) }
    }
}
