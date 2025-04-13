package com.compose.hangf_aos.views.screens.order

import com.compose.hangf_aos.data.model.Order

sealed class OrderIntent {
    data class AddOrder(val order: Order) : OrderIntent()
    data class GetOrder(val orderId: String) : OrderIntent()
    data object GetAllOrders : OrderIntent()
    data object GetOrdersByTime : OrderIntent()
    data object GetOrdersByStatus : OrderIntent()
    data object GetOrdersByCustomer : OrderIntent()
    data object GetOrdersByStore : OrderIntent()
    data class UpdateOrder(val order: Order) : OrderIntent()
    data class DeleteOrder(val orderId: String) : OrderIntent()
}