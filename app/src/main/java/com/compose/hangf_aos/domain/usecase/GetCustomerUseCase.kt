package com.compose.hangf_aos.domain.usecase

import com.compose.hangf_aos.data.Model.Customer
import com.compose.hangf_aos.data.repository.CustomerRepository

class GetCustomerUseCase(private val repository: CustomerRepository) {
    suspend operator fun invoke(phoneNumber: String): Result<Customer?> {
        return repository.getCustomer(phoneNumber)
    }
}