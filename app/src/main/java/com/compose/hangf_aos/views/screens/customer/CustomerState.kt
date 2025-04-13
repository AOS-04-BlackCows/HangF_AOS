package com.compose.hangf_aos.views.screens.customer

import com.compose.hangf_aos.data.model.Customer

sealed class CustomerState {
    data object Idle : CustomerState()
    data object Loading : CustomerState()
    data class Success(val customer: Customer?) : CustomerState()
    data class ListSuccess(val customers: List<Customer>) : CustomerState()
    data class Message(val message: String) : CustomerState() // 성공 메시지용
    data class Error(val message: String) : CustomerState()
}