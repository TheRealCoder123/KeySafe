package com.nextsolutions.keyysafe.auth.data.model

data class AuthResponse(
    val error: String = "",
    val isAuthenticated: Boolean,
)
