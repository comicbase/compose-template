package com.example.enterprise.core.domain.repository

import com.example.enterprise.core.model.UserProfile

interface ProfileRepository {
    suspend fun getProfile(): UserProfile
}

