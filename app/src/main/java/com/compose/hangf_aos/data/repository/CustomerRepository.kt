package com.compose.hangf_aos.data.repository

import com.compose.hangf_aos.data.Model.Customer
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CustomerRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    // 고객 추가
    suspend fun addCustomer(customer: Customer): Result<Unit> {
        return try {
            db.collection("customers")
                .document(customer.phone) // 전화번호를 Document ID로 사용
                .set(customer)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 고객 조회
    suspend fun getCustomer(phone: String): Result<Customer?> {
        return try {
            val snapshot = db.collection("customers")
                .document(phone)
                .get()
                .await()
            val customer = snapshot.toObject(Customer::class.java)
            Result.success(customer)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}