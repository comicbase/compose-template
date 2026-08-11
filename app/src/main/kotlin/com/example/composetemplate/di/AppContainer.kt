package com.example.composetemplate.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.composetemplate.core.common.DefaultDispatcherProvider
import com.example.composetemplate.core.data.DefaultArticleRepository
import com.example.composetemplate.core.data.DefaultProfileRepository
import com.example.composetemplate.core.database.InMemoryArticleLocalDataSource
import com.example.composetemplate.core.domain.GetProfileUseCase
import com.example.composetemplate.core.domain.ObserveArticleUseCase
import com.example.composetemplate.core.domain.ObserveArticlesUseCase
import com.example.composetemplate.core.domain.RefreshArticlesUseCase
import com.example.composetemplate.core.network.FakeArticleRemoteDataSource
import com.example.composetemplate.feature.article.ArticleViewModel
import com.example.composetemplate.feature.home.viewmodel.HomeViewModel
import com.example.composetemplate.feature.profile.ProfileViewModel

/**
 * 应用组合根（Composition Root）。只有 app 知道接口对应哪个实现。
 * 企业项目可在不改 Feature 的前提下，把这里替换为 Hilt/Koin 等 DI 框架。
 */
class AppContainer {
    private val articleRepository = DefaultArticleRepository(
        remote = FakeArticleRemoteDataSource(),
        local = InMemoryArticleLocalDataSource(),
        dispatchers = DefaultDispatcherProvider,
    )
    private val profileRepository = DefaultProfileRepository()

    val homeViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                observeArticles = ObserveArticlesUseCase(articleRepository),
                refreshArticles = RefreshArticlesUseCase(articleRepository),
            )
        }
    }

    val profileViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
        initializer { ProfileViewModel(GetProfileUseCase(profileRepository)) }
    }

    fun articleViewModelFactory(articleId: String): ViewModelProvider.Factory = viewModelFactory {
        initializer { ArticleViewModel(articleId, ObserveArticleUseCase(articleRepository)) }
    }
}

