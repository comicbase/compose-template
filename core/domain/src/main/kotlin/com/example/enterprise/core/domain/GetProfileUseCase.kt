package com.example.enterprise.core.domain

import com.example.enterprise.core.domain.repository.ProfileRepository
import com.example.enterprise.core.model.UserProfile

class GetProfileUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(): UserProfile = repository.getProfile()
}
