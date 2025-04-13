package com.compose.hangf_aos.data.repository.repoImpl

import com.compose.hangf_aos.data.model.Store
import com.compose.hangf_aos.data.repository.repoInterfaces.StoreRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : StoreRepository {
    private val storesRef = db.collection("stores")

    // 매장 추가
    override suspend fun addStore(store: Store): Result<Unit> {
        return try {
            val docRef = if (store.id.isEmpty()) {
                storesRef.document() // auto ID
            } else {
                storesRef.document(store.id)
            }
            docRef.set(store.copy(id = docRef.id)).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 매장 조회
    override suspend fun getStore(storeId: String): Result<Store?> {
        return try {
            val snapshot = storesRef.document(storeId).get().await()
            Result.success(snapshot.toObject(Store::class.java))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 전체 매장 조회
    override suspend fun getAllStores(): Result<List<Store>> {
        return try {
            val snapshot = storesRef.get().await()
            val stores = snapshot.documents.mapNotNull { it.toObject(Store::class.java) }
            Result.success(stores)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 등록 시간 순으로 정렬된 매장 목록 조회
    override suspend fun getStoresByTime(): Result<List<Store>> {
        return try {
            val snapshot = storesRef.orderBy("timestamp").get().await()
            val stores = snapshot.documents.mapNotNull { it.toObject(Store::class.java) }
            Result.success(stores)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 매장 수정
    override suspend fun updateStore(store: Store): Result<Unit> {
        return try {
            storesRef.document(store.id).set(store).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 매장 삭제
    override suspend fun deleteStore(storeId: String): Result<Unit> {
        return try {
            storesRef.document(storeId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}