package com.compose.hangf_aos.domain.usecase

import com.compose.hangf_aos.data.model.Order
import com.compose.hangf_aos.data.repository.OrderRepository

class OrderUseCase (
    private val orderRepository: OrderRepository
){
    suspend fun addOrder(order: Order): Result<Unit> {
        return orderRepository.addOrder(order)
    }
    suspend fun getOrder(orderId: String): Result<Order?> {
        return orderRepository.getOrder(orderId)
    }
    suspend fun getAllOrders(): Result<List<Order>> {
        return orderRepository.getAllOrders()
    }
    suspend fun getOrdersByTime(): Result<List<Order>> {
        return orderRepository.getOrdersByTime()
    }
    suspend fun getOrdersByStatus(status: String): Result<List<Order>> {
        return orderRepository.getOrdersByStatus(status)
    }
    suspend fun getOrdersByCustomer(customerId: String): Result<List<Order>> {
        return orderRepository.getOrdersByCustomer(customerId)
    }
    suspend fun getOrdersByStore(storeId: String): Result<List<Order>> {
        return orderRepository.getOrdersByStore(storeId)
    }
    suspend fun updateOrder(order: Order): Result<Unit> {
        return orderRepository.updateOrder(order)
    }
    suspend fun deleteOrder(orderId: String): Result<Unit> {
        return orderRepository.deleteOrder(orderId)
    }
}