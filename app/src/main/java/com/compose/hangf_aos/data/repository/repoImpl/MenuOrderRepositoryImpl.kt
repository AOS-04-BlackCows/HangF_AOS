package com.compose.hangf_aos.data.repository.repoImpl

import com.compose.hangf_aos.data.model.MenuOrder
import com.compose.hangf_aos.data.repository.repoInterfaces.MenuOrderRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MenuOrderRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : MenuOrderRepository {
    private val menuOrdersRef = db.collection("menuOrders")

    // 메뉴 주문 추가
    override suspend fun addMenuOrder(menuOrder: MenuOrder): Result<Unit> {
        return try {
            val docRef = if (menuOrder.id.isEmpty()) menuOrdersRef.document() else menuOrdersRef.document(menuOrder.id)
            docRef.set(menuOrder.copy(id = docRef.id)).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 메뉴 주문 조회
    override suspend fun getMenuOrder(menuOrderId: String): Result<MenuOrder?> {
        return try {
            val snapshot = menuOrdersRef.document(menuOrderId).get().await()
            Result.success(snapshot.toObject(MenuOrder::class.java))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 모든 메뉴 주문 조회
    override suspend fun getAllMenuOrders(): Result<List<MenuOrder>> {
        return try {
            val snapshot = menuOrdersRef.get().await()
            val orders = snapshot.documents.mapNotNull { it.toObject(MenuOrder::class.java) }
            Result.success(orders)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 특정 가게의 메뉴 주문 조회
    override suspend fun getMenuOrdersByStore(storeId: String): Result<List<MenuOrder>> {
        return try {
            val snapshot = menuOrdersRef.whereEqualTo("storeId", storeId).get().await()
            val orders = snapshot.documents.mapNotNull { it.toObject(MenuOrder::class.java) }
            Result.success(orders)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 특정 메뉴 아이디의 주문 조회
    override suspend fun getMenuOrdersByMenuId(menuId: String): Result<List<MenuOrder>> {
        return try {
            val snapshot = menuOrdersRef.whereEqualTo("menuId", menuId).get().await()
            val orders = snapshot.documents.mapNotNull { it.toObject(MenuOrder::class.java) }
            Result.success(orders)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 등록 시간 순으로 정렬된 메뉴 주문 조회
    override suspend fun getMenuOrdersByTime(): Result<List<MenuOrder>> {
        return try {
            val snapshot = menuOrdersRef.orderBy("timestamp").get().await()
            val orders = snapshot.documents.mapNotNull { it.toObject(MenuOrder::class.java) }
            Result.success(orders)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 메뉴 주문 수정
    override suspend fun updateMenuOrder(menuOrder: MenuOrder): Result<Unit> {
        return try {
            menuOrdersRef.document(menuOrder.id).set(menuOrder).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 메뉴 주문 삭제
    override suspend fun deleteMenuOrder(menuOrderId: String): Result<Unit> {
        return try {
            menuOrdersRef.document(menuOrderId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}