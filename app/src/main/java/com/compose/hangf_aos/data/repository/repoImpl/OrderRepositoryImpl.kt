package com.compose.hangf_aos.data.repository.repoImpl

import com.compose.hangf_aos.data.model.Order
import com.compose.hangf_aos.data.repository.repoInterfaces.OrderRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : OrderRepository {
    private val ordersRef = db.collection("orders")

    // 주문 추가
    override suspend fun addOrder(order: Order): Result<Unit> {
        return try {
            val docRef =
                if (order.id.isEmpty()) ordersRef.document() else ordersRef.document(order.id)
            docRef.set(order.copy(id = docRef.id)).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 주문 조회
    override suspend fun getOrder(orderId: String): Result<Order?> {
        return try {
            val snapshot = ordersRef.document(orderId).get().await()
            Result.success(snapshot.toObject(Order::class.java))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 모든 주문 조회
    override suspend fun getAllOrders(): Result<List<Order>> {
        return try {
            val snapshot = ordersRef.get().await()
            val orders = snapshot.documents.mapNotNull { it.toObject(Order::class.java) }
            Result.success(orders)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 특정 고객의 주문 조회
    override suspend fun getOrdersByCustomer(customerId: String): Result<List<Order>> {
        return try {
            val snapshot = ordersRef.whereEqualTo("customerId", customerId).get().await()
            val orders = snapshot.documents.mapNotNull { it.toObject(Order::class.java) }
            Result.success(orders)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 특정 가게의 주문 조회
    override suspend fun getOrdersByStore(storeId: String): Result<List<Order>> {
        return try {
            val snapshot = ordersRef.whereEqualTo("storeId", storeId).get().await()
            val orders = snapshot.documents.mapNotNull { it.toObject(Order::class.java) }
            Result.success(orders)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 등록 시간 순으로 정렬된 주문 목록 조회
    override suspend fun getOrdersByTime(): Result<List<Order>> {
        return try {
            val snapshot = ordersRef.orderBy("timestamp").get().await()
            val orders = snapshot.documents.mapNotNull { it.toObject(Order::class.java) }
            Result.success(orders)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 특정 상태의 주문 목록 조회
    override suspend fun getOrdersByStatus(status: String): Result<List<Order>> {
        return try {
            val snapshot = ordersRef.whereEqualTo("status", status).get().await()
            val orders = snapshot.documents.mapNotNull { it.toObject(Order::class.java) }
            Result.success(orders)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 주문 수정
    override suspend fun updateOrder(order: Order): Result<Unit> {
        return try {
            ordersRef.document(order.id).set(order).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 주문 삭제
    override suspend fun deleteOrder(orderId: String): Result<Unit> {
        return try {
            ordersRef.document(orderId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
//
//    override suspend fun getOrdersByMenu(menuId: String): Result<List<Order>> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun getOrdersByCategory(category: String): Result<List<Order>> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun getOrdersByPrice(minPrice: Double, maxPrice: Double): Result<List<Order>> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun getOrdersByRating(
//        minRating: Double,
//        maxRating: Double
//    ): Result<List<Order>> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun getOrdersByDeliveryTime(
//        minTime: Long,
//        maxTime: Long
//    ): Result<List<Order>> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun getOrdersByDeliveryFee(
//        minFee: Double,
//        maxFee: Double
//    ): Result<List<Order>> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun getOrdersByDeliveryMethod(method: String): Result<List<Order>> {
//        TODO("Not yet implemented")
//    }
}