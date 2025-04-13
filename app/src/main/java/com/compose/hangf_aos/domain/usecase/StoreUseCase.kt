package com.compose.hangf_aos.domain.usecase

import com.compose.hangf_aos.data.model.Store
import com.compose.hangf_aos.data.repository.repoInterfaces.StoreRepository
import javax.inject.Inject

class StoreUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    suspend fun addStore(store: Store): Result<Unit> {
        return storeRepository.addStore(store)
    }
    suspend fun getStore(storeId: String): Result<Store?> {
        return storeRepository.getStore(storeId)
    }
    suspend fun updateStore(store: Store): Result<Unit> {
        return storeRepository.updateStore(store)
    }
    suspend fun deleteStore(storeId: String): Result<Unit> {
        return storeRepository.deleteStore(storeId)
    }
}