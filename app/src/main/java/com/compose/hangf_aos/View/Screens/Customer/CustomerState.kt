package com.compose.hangf_aos.View.Screens.Customer

import com.compose.hangf_aos.data.Model.Customer

sealed class CustomerState {
    object Idle : CustomerState()
    object Loading : CustomerState()
    data class Success(val customer: Customer?) : CustomerState()
    data class Error(val message: String) : CustomerState()
}