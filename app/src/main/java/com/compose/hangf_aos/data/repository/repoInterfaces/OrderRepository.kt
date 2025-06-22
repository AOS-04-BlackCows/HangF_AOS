package com.compose.hangf_aos.data.repository.repoInterfaces

import com.compose.hangf_aos.data.model.Menu
import com.compose.hangf_aos.data.model.MenuOrder
import com.compose.hangf_aos.data.model.Order

interface OrderRepository {
    suspend fun addOrder(order: Order): Result<Unit>
    suspend fun getOrder(orderId: String): Result<Order?>
    suspend fun getAllOrders(): Result<List<Order>>
    suspend fun getOrdersByCustomer(customerId: String): Result<List<Order>>
    suspend fun getOrdersByStore(storeId: String): Result<List<Order>>
    suspend fun getOrdersByTime(): Result<List<Order>>
    suspend fun getOrdersByStatus(status: String): Result<List<Order>>
    suspend fun updateOrder(order: Order): Result<Unit>
    suspend fun deleteOrder(orderId: String): Result<Unit>
}