package com.compose.hangf_aos.data.repository.repoInterfaces

import com.compose.hangf_aos.data.model.Owner

interface OwnerRepository {
    suspend fun addOwner(owner: Owner): Result<Unit>
    suspend fun getOwner(ownerId: String): Result<Owner?>
    suspend fun getAllOwners(): Result<List<Owner>>
    suspend fun getOwnersByStore(storeId: String): Result<List<Owner>>
    suspend fun getOwnersByTime(): Result<List<Owner>>
    suspend fun updateOwner(owner: Owner): Result<Unit>
    suspend fun deleteOwner(ownerId: String): Result<Unit>
}