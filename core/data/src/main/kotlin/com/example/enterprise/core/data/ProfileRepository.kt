package com.example.enterprise.core.data

import com.example.enterprise.core.domain.repository.ProfileRepository
import com.example.enterprise.core.model.UserProfile

class DefaultProfileRepository : ProfileRepository {
    override suspend fun getProfile(): UserProfile = UserProfile(
        name = "示例用户",
        role = "Android Engineer",
        department = "Mobile Platform",
    )
}
