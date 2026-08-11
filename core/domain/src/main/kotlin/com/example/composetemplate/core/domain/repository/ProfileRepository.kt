package com.example.composetemplate.core.domain.repository

import com.example.composetemplate.core.model.UserProfile

interface ProfileRepository {
    suspend fun getProfile(): UserProfile
}
