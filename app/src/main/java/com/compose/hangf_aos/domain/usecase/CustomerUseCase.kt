package com.compose.hangf_aos.domain.usecase

import com.compose.hangf_aos.data.local.LocalStorage
import com.compose.hangf_aos.data.model.Customer
import com.compose.hangf_aos.data.repository.repoInterfaces.CustomerRepository
import javax.inject.Inject

class CustomerUseCase @Inject constructor(
    private val customerRepository: CustomerRepository,
    private val localStorage: LocalStorage
) {

    suspend fun addCustomer(customer: Customer): Result<Unit> {
        val result = customerRepository.addCustomer(customer)
        if (result.isSuccess) {
            localStorage.saveCustomer(customer.name, customer.phone)
        }
        return result
    }

    suspend fun getCustomer(phone: String): Result<Customer?> {
        return customerRepository.getCustomer(phone)
    }

    suspend fun getLocalCustomer(): Result<Customer?> {
        return try {
            val (name, phone) = localStorage.getCustomer()
            if (!name.isNullOrBlank() && !phone.isNullOrBlank()) {
                Result.success(Customer(name, phone))
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun clearLocalCustomer() {
        localStorage.clearCustomer()
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