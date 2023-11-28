package com.nextsolutions.keyysafe.auth.ui.AuthScreen

import com.nextsolutions.keyysafe.auth.domain.enums.AuthenticationType

data class AuthState (
    val isAuthenticated: Boolean = false,
    val error: String = "",
    val authenticationType: AuthenticationType = AuthenticationType.PASSWORD
)