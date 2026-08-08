package com.example.enterprise.feature.article

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.enterprise.core.designsystem.component.AppToolbar
import com.example.enterprise.core.designsystem.component.EmptyContent
import com.example.enterprise.core.designsystem.component.LoadingContent
import com.example.enterprise.core.designsystem.theme.LocalAppSpacing

@Composable
fun ArticleRoute(
    articleId: String,
    navigator: ArticleNavigator,
    factory: ViewModelProvider.Factory,
) {
    val viewModel: ArticleViewModel = viewModel(key = "article-$articleId", factory = factory)
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(viewModel, navigator) {
        viewModel.effects.collect { navigator.goBack() }
    }
    ArticleScreen(state = state, onEvent = viewModel::onEvent)
}

@Composable
fun ArticleScreen(state: ArticleUiState, onEvent: (ArticleEvent) -> Unit) {
    Scaffold(
        topBar = {
            AppToolbar(
                title = "文章详情",
                navigationLabel = "返回",
                onNavigationClick = { onEvent(ArticleEvent.BackClicked) },
            )
        },
    ) { innerPadding ->
        when {
            state.isLoading -> LoadingContent()
            state.article == null -> EmptyContent("内容不存在")
            else -> Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding)
                    .padding(LocalAppSpacing.current.medium),
                verticalArrangement = Arrangement.spacedBy(LocalAppSpacing.current.medium),
            ) {
                Text(state.article.category, color = MaterialTheme.colorScheme.primary)
                Text(state.article.title, style = MaterialTheme.typography.headlineMedium)
                Text(state.article.summary, style = MaterialTheme.typography.bodyLarge)
                Text(
                    "这是可替换为服务端正文的模板区域。详情页只接收 UiState，数据读取由 ViewModel 和 UseCase 完成。",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

