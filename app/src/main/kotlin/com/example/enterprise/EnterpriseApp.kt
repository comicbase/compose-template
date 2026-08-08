package com.example.enterprise

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.example.enterprise.di.AppContainer
import com.example.enterprise.feature.article.ArticleNavigator
import com.example.enterprise.feature.article.ArticleRoute
import com.example.enterprise.feature.home.navigation.HomeNavigator
import com.example.enterprise.feature.home.ui.HomeRoute
import com.example.enterprise.feature.profile.ProfileNavigator
import com.example.enterprise.feature.profile.ProfileRoute
import com.example.enterprise.navigation.AppDestination

/**
 * 顶层导航宿主。模板使用轻量状态回退栈，后续可替换 Navigation Compose；
 * 因为 Feature 依赖的是 Navigator 接口，所以替换不会波及 Screen 和 ViewModel。
 */
@Composable
fun EnterpriseApp(container: AppContainer) {
    val backStack = remember { listOf<AppDestination>(AppDestination.Home).toMutableStateList() }
    val goBack = remember(backStack) { { backStack.popIfPossible() } }
    BackHandler(enabled = backStack.size > 1, onBack = goBack)

    when (val destination = backStack.last()) {
        AppDestination.Home -> HomeRoute(
            navigator = remember(backStack) {
                object : HomeNavigator {
                    override fun openArticle(id: String) { backStack += AppDestination.Article(id) }
                    override fun openProfile() { backStack += AppDestination.Profile }
                }
            },
            factory = container.homeViewModelFactory,
        )
        AppDestination.Profile -> ProfileRoute(
            navigator = remember(backStack) {
                object : ProfileNavigator { override fun goBack() = goBack() }
            },
            factory = container.profileViewModelFactory,
        )
        is AppDestination.Article -> ArticleRoute(
            articleId = destination.id,
            navigator = remember(backStack) {
                object : ArticleNavigator { override fun goBack() = goBack() }
            },
            factory = container.articleViewModelFactory(destination.id),
        )
    }
}

private fun SnapshotStateList<AppDestination>.popIfPossible() {
    if (size > 1) removeAt(lastIndex)
}
