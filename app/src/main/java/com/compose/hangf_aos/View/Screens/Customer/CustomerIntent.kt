package com.compose.hangf_aos.View.Screens.Customer

import com.compose.hangf_aos.data.model.Customer

sealed class CustomerIntent {
    data class AddCustomer(val customer: Customer) : CustomerIntent()
    data class GetCustomer(val phoneNumber: String) : CustomerIntent()
    data object GetAllCustomers : CustomerIntent()
    data class UpdateCustomer(val customer: Customer) : CustomerIntent()
    data class DeleteCustomer(val phoneNumber: String) : CustomerIntent()
}