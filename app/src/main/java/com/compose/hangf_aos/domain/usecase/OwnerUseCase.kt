package com.compose.hangf_aos.domain.usecase

import com.compose.hangf_aos.data.model.Owner
import com.compose.hangf_aos.data.repository.OwnerRepository

class OwnerUseCase(
    private val ownerRepository: OwnerRepository
) {
    suspend fun addOwner(Owner: Owner): Result<Unit> {
        return ownerRepository.addOwner(Owner)
    }

    suspend fun getOwner(phone: String): Result<Owner?> {
        return ownerRepository.getOwner(phone)
    }

    suspend fun getOwnersByTime(): Result<List<Owner>> {
        return ownerRepository.getOwnersByTime()
    }

    suspend fun updateOwner(Owner: Owner): Result<Unit> {
        return ownerRepository.updateOwner(Owner)
    }

    suspend fun deleteOwner(phone: String): Result<Unit> {
        return ownerRepository.deleteOwner(phone)
    }
}