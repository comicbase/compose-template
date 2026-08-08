package com.example.enterprise.feature.profile

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
import com.example.enterprise.core.designsystem.component.LoadingContent
import com.example.enterprise.core.designsystem.component.PrimaryButton
import com.example.enterprise.core.designsystem.theme.LocalAppSpacing

@Composable
fun ProfileRoute(navigator: ProfileNavigator, factory: ViewModelProvider.Factory) {
    val viewModel: ProfileViewModel = viewModel(factory = factory)
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    LaunchedEffect(viewModel, navigator) {
        viewModel.effects.collect { navigator.goBack() }
    }
    ProfileScreen(state = state, onEvent = viewModel::onEvent)
}

@Composable
fun ProfileScreen(state: ProfileUiState, onEvent: (ProfileEvent) -> Unit) {
    Scaffold(
        topBar = {
            AppToolbar(
                title = "个人中心",
                navigationLabel = "返回",
                onNavigationClick = { onEvent(ProfileEvent.BackClicked) },
            )
        },
    ) { innerPadding ->
        when {
            state.isLoading -> LoadingContent()
            state.errorMessage != null -> Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding)
                    .padding(LocalAppSpacing.current.large),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(state.errorMessage)
                PrimaryButton("重试", { onEvent(ProfileEvent.Retry) })
            }
            state.profile != null -> Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding)
                    .padding(LocalAppSpacing.current.large),
                verticalArrangement = Arrangement.spacedBy(LocalAppSpacing.current.medium),
            ) {
                Text(state.profile.name, style = MaterialTheme.typography.headlineMedium)
                Text("职位：${state.profile.role}")
                Text("部门：${state.profile.department}")
            }
        }
    }
}

