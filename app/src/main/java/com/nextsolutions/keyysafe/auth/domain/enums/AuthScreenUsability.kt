package com.nextsolutions.keyysafe.auth.domain.enums

enum class AuthScreenUsability {
    //Initial Authentication
    AUTHENTICATE,
    //When reopening the app
    RE_AUTHENTICATE,
    //Get a result from authentication
    CONFIRM_AUTHENTICATION
}

fun decodeAuthScreenUsability(value: String): AuthScreenUsability {
    return AuthScreenUsability.valueOf(value)
}