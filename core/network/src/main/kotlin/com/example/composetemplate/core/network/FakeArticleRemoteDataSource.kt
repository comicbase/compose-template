package com.example.composetemplate.core.network

import com.example.composetemplate.core.model.Article
import kotlinx.coroutines.delay

/** 可运行模板使用的确定性假数据源；接入真实后端时只替换该实现。 */
class FakeArticleRemoteDataSource : ArticleRemoteDataSource {
    override suspend fun fetchArticles(): List<Article> {
        delay(350)
        return listOf(
            Article("compose-architecture", "可维护的 Compose 架构", "用状态驱动 UI，并让数据单向流动。", "架构"),
            Article("feature-modules", "用 Feature 划分业务边界", "独立模块减少耦合和多人协作冲突。", "模块化"),
            Article("design-system", "建立统一 Design System", "统一颜色、间距和可复用组件。", "设计系统"),
        )
    }
}

