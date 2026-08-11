package com.example.composetemplate.core.domain

import com.example.composetemplate.core.domain.repository.ArticleRepository
import com.example.composetemplate.core.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/** 用例封装搜索规则，ViewModel 不直接操作仓库细节。 */
class ObserveArticlesUseCase(private val repository: ArticleRepository) {
    operator fun invoke(query: String): Flow<List<Article>> =
        repository.observeArticles().map { articles ->
            val normalized = query.trim()
            if (normalized.isEmpty()) {
                articles
            } else {
                articles.filter { article ->
                    article.title.contains(normalized, ignoreCase = true) ||
                        article.summary.contains(normalized, ignoreCase = true) ||
                        article.category.contains(normalized, ignoreCase = true)
                }
            }
        }
}
