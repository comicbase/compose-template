package com.example.composetemplate.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetemplate.core.domain.GetProfileUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val getProfile: GetProfileUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()
    private val _effects = MutableSharedFlow<ProfileEffect>(extraBufferCapacity = 1)
    val effects = _effects.asSharedFlow()

    init { load() }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.BackClicked -> _effects.tryEmit(ProfileEffect.GoBack)
            ProfileEvent.Retry -> load()
        }
    }

    private fun load() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState(isLoading = true)
            _uiState.value = runCatching { getProfile() }.fold(
                onSuccess = { ProfileUiState(isLoading = false, profile = it) },
                onFailure = { ProfileUiState(isLoading = false, errorMessage = it.message ?: "加载失败") },
            )
        }
    }
}

