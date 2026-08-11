package com.example.composetemplate.feature.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composetemplate.feature.home.event.HomeEffect
import com.example.composetemplate.feature.home.navigation.HomeNavigator
import com.example.composetemplate.feature.home.viewmodel.HomeViewModel

/** Route 是状态容器边界；HomeScreen 保持无状态并可独立 Preview/测试。 */
@Composable
fun HomeRoute(navigator: HomeNavigator, factory: ViewModelProvider.Factory) {
    val viewModel: HomeViewModel = viewModel(factory = factory)
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(viewModel, navigator) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is HomeEffect.OpenArticle -> navigator.openArticle(effect.id)
                HomeEffect.OpenProfile -> navigator.openProfile()
            }
        }
    }

    HomeScreen(state = state, onEvent = viewModel::onEvent)
}

