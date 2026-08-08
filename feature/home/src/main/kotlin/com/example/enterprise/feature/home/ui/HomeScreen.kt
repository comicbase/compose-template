package com.example.enterprise.feature.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.enterprise.core.designsystem.component.AppToolbar
import com.example.enterprise.core.designsystem.component.EmptyContent
import com.example.enterprise.core.designsystem.component.LoadingContent
import com.example.enterprise.core.designsystem.theme.EnterpriseTheme
import com.example.enterprise.core.model.Article
import com.example.enterprise.feature.home.event.HomeEvent
import com.example.enterprise.feature.home.state.HomeUiState

/** Screen 只组织 Section，不含仓库、网络、导航控制器或业务规则。 */
@Composable
fun HomeScreen(state: HomeUiState, onEvent: (HomeEvent) -> Unit) {
    Scaffold(
        topBar = {
            AppToolbar(
                title = "企业资讯",
                actions = {
                    TextButton(onClick = { onEvent(HomeEvent.ProfileClicked) }) { Text("我的") }
                },
            )
        },
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            when {
                state.isLoading && state.articles.isEmpty() -> LoadingContent()
                state.errorMessage != null && state.articles.isEmpty() -> ErrorSection(
                    message = state.errorMessage,
                    onRetry = { onEvent(HomeEvent.Refresh) },
                )
                state.showEmpty -> EmptyContent("没有匹配的内容")
                else -> HomeContent(
                    state = state,
                    onQueryChanged = { onEvent(HomeEvent.QueryChanged(it)) },
                    onArticleClick = { onEvent(HomeEvent.ArticleClicked(it)) },
                    onRefresh = { onEvent(HomeEvent.Refresh) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    EnterpriseTheme {
        HomeScreen(
            state = HomeUiState(
                isLoading = false,
                articles = listOf(Article("1", "Compose 架构", "状态驱动 UI", "架构")),
            ),
            onEvent = {},
        )
    }
}

