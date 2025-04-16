package com.compose.hangf_aos.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey

enum class UserType {
    CUSTOMER, STORE_OWNER
}

class Preferences (
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val USER_TYPE = stringPreferencesKey("user_type")
        val USER_NAME = stringPreferencesKey("user_id")
        val USER_PASSWORD = stringPreferencesKey("user_password")
        val USER_PHONE = stringPreferencesKey("user_phone")
    }
    
    suspend fun setUserData(
        userType: UserType,
        userName: String,
        userPassword: String,
        userPhone: String
    ) {
        dataStore.edit {
            it[USER_TYPE] = userType.name
            it[USER_NAME] = userName
            it[USER_PASSWORD] = userPassword
            it[USER_PHONE] = userPhone
        }
    }
    val userType = dataStore.data.map {
        UserType.valueOf(it[USER_TYPE] ?: i[UserType.CUSTOMER])

    }
}