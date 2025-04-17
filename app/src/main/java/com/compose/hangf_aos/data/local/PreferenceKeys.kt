package com.compose.hangf_aos.data.local

import androidx.datastore.preferences.core.stringPreferencesKey

object CustomerPreferenceKeys  {
    val NAME = stringPreferencesKey("name")
    val PHONE = stringPreferencesKey("phone")
}
object StoreOwnerPreferenceKeys {
    val STORE_ID = stringPreferencesKey("storeId")
    val NAME = stringPreferencesKey("name")
    val LOGIN_ID = stringPreferencesKey("loginId")
    val PASSWORD = stringPreferencesKey("password")
    val PHONE = stringPreferencesKey("phone")
}