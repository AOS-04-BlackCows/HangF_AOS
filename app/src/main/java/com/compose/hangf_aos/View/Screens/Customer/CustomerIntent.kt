package com.compose.hangf_aos.View.Screens.Customer

import com.compose.hangf_aos.data.Model.Customer

sealed class CustomerIntent {
    data class AddCustomer(val customer: Customer) : CustomerIntent()
    data class GetCustomer(val phoneNumber: String) : CustomerIntent()
}