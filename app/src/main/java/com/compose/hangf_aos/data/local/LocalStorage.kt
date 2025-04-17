package com.compose.hangf_aos.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.compose.hangf_aos.data.model.Customer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import javax.inject.Inject

private val Context.customerDataStore by preferencesDataStore(name = "customer_prefs")
private val Context.storeOwnerDataStore by preferencesDataStore(name = "store_owner_prefs")

class LocalStorage @Inject constructor(
    @ApplicationContext private val context: Context
){
    companion object {
        private val NAME_KEY = stringPreferencesKey("name")
        private val PHONE_KEY = stringPreferencesKey("phone")
        private val STORE_ID_KEY = stringPreferencesKey("storeId")
        private val LOGIN_ID_KEY = stringPreferencesKey("loginId")
        private val PASSWORD_KEY = stringPreferencesKey("password")
    }
    val customer: Flow<Customer?> = context.customerDataStore.data
        .map { prefs ->
            val name = prefs[NAME_KEY]
            val phone = prefs[PHONE_KEY]
            if (!name.isNullOrBlank() && !phone.isNullOrBlank()) {
                Customer(name = name, phone = phone)
            } else {
                null
            }
        }

    suspend fun saveCustomer(name: String, phone: String) {
        context.customerDataStore.edit { prefs ->
            prefs[NAME_KEY] = name
            prefs[PHONE_KEY] = phone
        }
    }

    suspend fun getCustomer(): Pair<String?, String?> {
        val prefs = context.customerDataStore.data.first()
        return Pair(prefs[NAME_KEY], prefs[PHONE_KEY])
    }

    suspend fun clearCustomer() {
        context.customerDataStore.edit { it.clear() }
    }

    suspend fun saveStoreOwner(storeId: String, name: String, loginId: String, password: String, phone: String) {
        context.storeOwnerDataStore.edit { prefs ->
            prefs[STORE_ID_KEY] = storeId
            prefs[NAME_KEY] = name
            prefs[LOGIN_ID_KEY] = loginId
            prefs[PASSWORD_KEY] = password
            prefs[PHONE_KEY] = phone
        }
    }

    suspend fun getStoreOwner(): Triple<String?, String?, String?> {
        val prefs = context.storeOwnerDataStore.data.first()
        return Triple(prefs[STORE_ID_KEY], prefs[NAME_KEY], prefs[PHONE_KEY])
    }

    suspend fun clearStoreOwner() {
        context.storeOwnerDataStore.edit { it.clear() }
    }

}
