package com.compose.hangf_aos.domain.usecase

import com.compose.hangf_aos.data.model.Customer
import com.compose.hangf_aos.data.repository.CustomerRepository

class CustomerUseCase(
    private val customerRepository: CustomerRepository
) {

    suspend fun addCustomer(customer: Customer): Result<Unit> {
        return customerRepository.addCustomer(customer)
    }

    suspend fun getCustomer(phone: String): Result<Customer?> {
        return customerRepository.getCustomer(phone)
    }

    suspend fun getAllCustomers(): Result<List<Customer>> {
        return customerRepository.getAllCustomers()
    }

    suspend fun getCustomersByTime(): Result<List<Customer>> {
        return customerRepository.getCustomersByTime()
    }

    suspend fun updateCustomer(customer: Customer): Result<Unit> {
        return customerRepository.updateCustomer(customer)
    }

    suspend fun deleteCustomer(phone: String): Result<Unit> {
        return customerRepository.deleteCustomer(phone)
    }
}