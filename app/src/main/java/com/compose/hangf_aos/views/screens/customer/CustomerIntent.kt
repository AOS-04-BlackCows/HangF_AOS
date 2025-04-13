package com.compose.hangf_aos.views.screens.customer

import com.compose.hangf_aos.data.model.Customer

sealed class CustomerIntent {
    data class AddCustomer(val customer: Customer) : CustomerIntent()
    data class GetCustomer(val phoneNumber: String) : CustomerIntent()
    data object GetAllCustomers : CustomerIntent()
    data class UpdateCustomer(val customer: Customer) : CustomerIntent()
    data class DeleteCustomer(val phoneNumber: String) : CustomerIntent()
}