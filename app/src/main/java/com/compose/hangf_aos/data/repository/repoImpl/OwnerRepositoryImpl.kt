package com.compose.hangf_aos.data.repository.repoImpl

import com.compose.hangf_aos.data.model.Owner
import com.compose.hangf_aos.data.repository.repoInterfaces.OwnerRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OwnerRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : OwnerRepository {
    private val ownersRef = db.collection("owners")

    // 점주 추가
    override suspend fun addOwner(owner: Owner): Result<Unit> {
        return try {
            val docRef = if (owner.id.isEmpty()) ownersRef.document() else ownersRef.document(owner.id)
            docRef.set(owner.copy(id = docRef.id)).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    //점주 조회
    override suspend fun getOwner(ownerId: String): Result<Owner?> {
        return try {
            val snapshot = ownersRef.document(ownerId).get().await()
            Result.success(snapshot.toObject(Owner::class.java))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 특정 Store에 속한 점주 목록 조회
    override suspend fun getOwnersByStore(storeId: String): Result<List<Owner>> {
        return try {
            val snapshot = ownersRef.whereEqualTo("storeId", storeId).get().await()
            val owners = snapshot.documents.mapNotNull { it.toObject(Owner::class.java) }
            Result.success(owners)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 모든 점주 조회
    override suspend fun getAllOwners(): Result<List<Owner>> {
        return try {
            val snapshot = ownersRef.get().await()
            val owners = snapshot.documents.mapNotNull { it.toObject(Owner::class.java) }
            Result.success(owners)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 등록 시간 순으로 정렬된 점주 목록 조회
    override suspend fun getOwnersByTime(): Result<List<Owner>> {
        return try {
            val snapshot = ownersRef.orderBy("timestamp").get().await()
            val owners = snapshot.documents.mapNotNull { it.toObject(Owner::class.java) }
            Result.success(owners)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 점주 정보 수정
    override suspend fun updateOwner(owner: Owner): Result<Unit> {
        return try {
            ownersRef.document(owner.id).set(owner).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 점주 삭제
    override suspend fun deleteOwner(ownerId: String): Result<Unit> {
        return try {
            ownersRef.document(ownerId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}