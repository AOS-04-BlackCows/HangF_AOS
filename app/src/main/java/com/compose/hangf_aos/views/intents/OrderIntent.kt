package com.compose.hangf_aos.views.intents

import com.compose.hangf_aos.data.model.Order

sealed class OrderIntent {
    data class AddOrder(val order: Order) : OrderIntent()
    data class GetOrder(val orderId: String) : OrderIntent()
    data object GetAllOrders : OrderIntent()
    data object GetOrdersByTime : OrderIntent()
    data class GetOrdersByStatus(val status: String) : OrderIntent()
    data class GetOrdersByCustomer(val customerId: String) : OrderIntent()
    data class GetOrdersByStore(val storeId: String) : OrderIntent()
    data class UpdateOrder(val order: Order) : OrderIntent()
    data class DeleteOrder(val orderId: String) : OrderIntent()
}