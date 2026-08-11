package com.example.composetemplate.feature.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.composetemplate.core.designsystem.component.PrimaryButton
import com.example.composetemplate.core.designsystem.theme.LocalAppSpacing
import com.example.composetemplate.core.model.Article
import com.example.composetemplate.feature.home.state.HomeUiState

@Composable
internal fun HomeContent(
    state: HomeUiState,
    onQueryChanged: (String) -> Unit,
    onArticleClick: (String) -> Unit,
    onRefresh: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = LocalAppSpacing.current.medium),
        verticalArrangement = Arrangement.spacedBy(LocalAppSpacing.current.medium),
    ) {
        SearchSection(query = state.query, onQueryChanged = onQueryChanged)
        FeedSection(
            articles = state.articles,
            isRefreshing = state.isLoading,
            onArticleClick = onArticleClick,
            onRefresh = onRefresh,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun SearchSection(query: String, onQueryChanged: (String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChanged,
        label = { Text("搜索文章") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun FeedSection(
    articles: List<Article>,
    isRefreshing: Boolean,
    onArticleClick: (String) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(LocalAppSpacing.current.small),
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text("推荐内容", style = MaterialTheme.typography.titleMedium)
                PrimaryButton(
                    text = if (isRefreshing) "刷新中…" else "刷新",
                    onClick = onRefresh,
                    modifier = Modifier.fillMaxWidth(0.32f),
                )
            }
        }
        items(items = articles, key = Article::id) { article ->
            ArticleCard(article = article, onClick = { onArticleClick(article.id) })
        }
    }
}

@Composable
private fun ArticleCard(article: Article, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Column(
            modifier = Modifier.padding(LocalAppSpacing.current.medium),
            verticalArrangement = Arrangement.spacedBy(LocalAppSpacing.current.small),
        ) {
            Text(article.category, color = MaterialTheme.colorScheme.primary)
            Text(article.title, style = MaterialTheme.typography.titleMedium)
            Text(article.summary, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
internal fun ErrorSection(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(LocalAppSpacing.current.large),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(message)
        PrimaryButton(
            text = "重试",
            onClick = onRetry,
            modifier = Modifier.padding(top = LocalAppSpacing.current.medium),
        )
    }
}

