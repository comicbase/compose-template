package com.example.enterprise.feature.home.state

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class HomeUiStateTest {
    @Test fun `empty is shown only after successful loading`() {
        assertFalse(HomeUiState(isLoading = true).showEmpty)
        assertFalse(HomeUiState(isLoading = false, errorMessage = "error").showEmpty)
        assertTrue(HomeUiState(isLoading = false).showEmpty)
    }
}

