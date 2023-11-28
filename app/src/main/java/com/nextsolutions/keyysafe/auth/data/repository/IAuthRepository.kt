package com.nextsolutions.keyysafe.auth.data.repository

import com.nextsolutions.keyysafe.auth.data.model.AuthResponse
import com.nextsolutions.keyysafe.auth.domain.repository.AuthRepository
import com.nextsolutions.keyysafe.common.data.preferences.PreferencesKeys
import com.nextsolutions.keyysafe.common.data.preferences.PreferencesManager
import javax.inject.Inject

class IAuthRepository @Inject constructor(
    private val prefs: PreferencesManager
) : AuthRepository {
    override suspend fun authenticateWithPassword(password: String): AuthResponse {
        val masterPassword = prefs.getString(PreferencesKeys.MASTER_PASSWORD_KEY, null)
        return if (masterPassword == null){
            AuthResponse(
                "Master Password has not been created",
                isAuthenticated = false,
            )
        }else if(masterPassword != password){
            AuthResponse(
                "Password is wrong, try again",
                isAuthenticated = false,
            )
        }else {
            AuthResponse(
                isAuthenticated = true,
            )
        }
    }

    override suspend fun authenticateWithPIN(pin: Int): AuthResponse {
        val masterPin = prefs.getInt(PreferencesKeys.PIN_KEY, 0)
        return if (masterPin == 0){
            AuthResponse(
                "PIN has not been added",
                isAuthenticated = false,
            )
        }else if(masterPin != pin){
            AuthResponse(
                "PIN is wrong, try again",
                isAuthenticated = false
            )
        }else {
            AuthResponse(
                isAuthenticated = true
            )
        }
    }

}