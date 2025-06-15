package com.compose.hangf_aos.views.states

import com.compose.hangf_aos.data.model.MenuOrder

sealed class MenuOrderState {
    data object Idle : MenuOrderState()
    data object Loading : MenuOrderState()
    data class Success(val data: Any?) : MenuOrderState()
    data class Error(val message: String) : MenuOrderState()
    data class ListSuccess(val menuOrders: List<MenuOrder>) : MenuOrderState()
    data class Message(val message: String) : MenuOrderState()
}