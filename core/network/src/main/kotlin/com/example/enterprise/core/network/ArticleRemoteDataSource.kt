package com.example.enterprise.core.network

import com.example.enterprise.core.model.Article

/** 网络边界。真实项目可在此模块用 Retrofit/Ktor 实现，Feature 无需感知替换。 */
interface ArticleRemoteDataSource {
    suspend fun fetchArticles(): List<Article>
}

