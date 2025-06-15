package com.compose.hangf_aos.data.repository.repoImpl

import com.compose.hangf_aos.data.model.Customer
import com.compose.hangf_aos.data.repository.repoInterfaces.CustomerRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
) : CustomerRepository {
    private val customersRef = db.collection("customers")

    // 고객 추가
    override suspend fun addCustomer(customer: Customer): Result<Unit> {
        return try {
            customersRef
                .document(customer.phone) // 전화번호를 Document ID로 사용
                .set(customer)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 고객 조회
    override suspend fun getCustomer(phone: String): Result<Customer?> {
        return try {
            val snapshot = customersRef.document(phone).get().await()
            Result.success(snapshot.toObject(Customer::class.java))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 모든 고객 조회
    override suspend fun getAllCustomers(): Result<List<Customer>> {
        return try {
            val snapshot = customersRef.get().await()
            val customers = snapshot.documents.mapNotNull { it.toObject(Customer::class.java) }
            Result.success(customers)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 등록 시간 순으로 정렬된 고객 목록 조회
    override suspend fun getCustomersByTime(): Result<List<Customer>> {
        return try {
            val snapshot = customersRef.orderBy("timestamp").get().await()
            val customers = snapshot.documents.mapNotNull { it.toObject(Customer::class.java) }
            Result.success(customers)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 고객 정보 수정
    override suspend fun updateCustomer(customer: Customer): Result<Unit> {
        return try {
            customersRef.document(customer.phone).set(customer).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 고객 삭제
    override suspend fun deleteCustomer(phone: String): Result<Unit> {
        return try {
            customersRef.document(phone).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}