package com.compose.hangf_aos.data.repository.repoInterfaces

import com.compose.hangf_aos.data.model.Customer

interface CustomerRepository {
    suspend fun addCustomer(customer: Customer): Result<Unit>
    suspend fun getCustomer(phone: String): Result<Customer?>
    suspend fun getAllCustomers(): Result<List<Customer>>
    suspend fun getCustomersByTime(): Result<List<Customer>>
    suspend fun updateCustomer(customer: Customer): Result<Unit>
    suspend fun deleteCustomer(phone: String): Result<Unit>
}