package com.compose.hangf_aos.View.Screens.Order

import com.compose.hangf_aos.data.model.Order

sealed class OrderState {
    data object Idle : OrderState()
    data object Loading : OrderState()
    data class Success(val order: Order?) : OrderState()
    data class Error(val message: String) : OrderState()
    data class ListSuccess(val orders: List<Order>) : OrderState()
    data class Message(val message: String) : OrderState()
}