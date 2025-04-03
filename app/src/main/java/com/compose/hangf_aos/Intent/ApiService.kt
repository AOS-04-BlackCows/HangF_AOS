package com.compose.hangf_aos.Intent

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/api/customers")
    suspend fun getCustomers(): Response<List<Customer>>
}

data class Customer(
    val id: Long,
    val name: String,
    val phoneNumber: String
)