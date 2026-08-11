package com.example.composetemplate.core.domain

import com.example.composetemplate.core.domain.repository.ArticleRepository
import com.example.composetemplate.core.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ObserveArticlesUseCaseTest {
    private val articles = listOf(
        Article("1", "Compose 架构", "状态驱动 UI", "架构"),
        Article("2", "自动化测试", "稳定交付", "质量"),
    )

    @Test
    fun `query filters title summary and category`() = runTest {
        val useCase = ObserveArticlesUseCase(FakeArticleRepository(articles))

        assertEquals(listOf("1"), useCase("状态").first().map(Article::id))
        assertEquals(listOf("2"), useCase("质量").first().map(Article::id))
        assertEquals(articles, useCase("").first())
    }
}

private class FakeArticleRepository(private val articles: List<Article>) : ArticleRepository {
    override fun observeArticles(): Flow<List<Article>> = flowOf(articles)
    override suspend fun refresh(): Result<Unit> = Result.success(Unit)
}
