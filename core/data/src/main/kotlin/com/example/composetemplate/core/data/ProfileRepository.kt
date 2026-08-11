package com.example.composetemplate.core.data

import com.example.composetemplate.core.domain.repository.ProfileRepository
import com.example.composetemplate.core.model.UserProfile

class DefaultProfileRepository : ProfileRepository {
    override suspend fun getProfile(): UserProfile = UserProfile(
        name = "示例用户",
        role = "Android Engineer",
        department = "Mobile Platform",
    )
}
