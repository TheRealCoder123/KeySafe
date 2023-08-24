package com.nextsolutions.keyysafe.common.data.data_store

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val MASTER_PASSWORD_KEY = stringPreferencesKey(name = "m_p_k")
    val PIN_KEY = intPreferencesKey(name = "pin")
}