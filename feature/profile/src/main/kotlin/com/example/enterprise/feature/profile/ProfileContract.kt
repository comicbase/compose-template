package com.example.enterprise.feature.profile

import com.example.enterprise.core.model.UserProfile

data class ProfileUiState(
    val isLoading: Boolean = true,
    val profile: UserProfile? = null,
    val errorMessage: String? = null,
)

sealed interface ProfileEvent {
    data object BackClicked : ProfileEvent
    data object Retry : ProfileEvent
}

sealed interface ProfileEffect {
    data object GoBack : ProfileEffect
}

interface ProfileNavigator {
    fun goBack()
}

