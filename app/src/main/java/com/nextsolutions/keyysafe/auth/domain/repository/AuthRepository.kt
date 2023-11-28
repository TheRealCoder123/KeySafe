package com.nextsolutions.keyysafe.auth.domain.repository

import com.nextsolutions.keyysafe.auth.data.model.AuthResponse

interface AuthRepository {
    suspend fun authenticateWithPassword(password: String) : AuthResponse
    suspend fun authenticateWithPIN(pin: Int) : AuthResponse
}