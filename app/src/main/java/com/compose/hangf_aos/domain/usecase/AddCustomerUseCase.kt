package com.compose.hangf_aos.domain.usecase

import com.compose.hangf_aos.data.Model.Customer
import com.compose.hangf_aos.data.repository.CustomerRepository

class AddCustomerUseCase(private val repository: CustomerRepository) {
    suspend operator fun invoke(customer: Customer): Result<Unit> {
        return repository.addCustomer(customer)
    }
}