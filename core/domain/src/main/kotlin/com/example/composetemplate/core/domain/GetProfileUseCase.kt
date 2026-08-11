package com.example.composetemplate.core.domain

import com.example.composetemplate.core.domain.repository.ProfileRepository
import com.example.composetemplate.core.model.UserProfile

class GetProfileUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(): UserProfile = repository.getProfile()
}
