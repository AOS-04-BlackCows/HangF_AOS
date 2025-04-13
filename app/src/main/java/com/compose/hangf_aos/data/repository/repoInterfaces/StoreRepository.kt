package com.compose.hangf_aos.data.repository.repoInterfaces

import com.compose.hangf_aos.data.model.Store

interface StoreRepository {
    suspend fun addStore(store: Store): Result<Unit>
    suspend fun getStore(storeId: String): Result<Store?>
    suspend fun getAllStores(): Result<List<Store>>
    suspend fun getStoresByTime(): Result<List<Store>>
    suspend fun updateStore(store: Store): Result<Unit>
    suspend fun deleteStore(storeId: String): Result<Unit>
}