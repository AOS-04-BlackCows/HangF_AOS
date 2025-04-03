package com.compose.hangf_aos.repository

import com.compose.hangf_aos.Intent.Customer
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class FirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore
){

    private val db = Firebase.firestore

    fun addCustomer(customer: Customer, onResult: (Boolean) -> Unit) {
        db.collection("customers")
            .document(customer.phoneNumber)  // 전화번호를 Document ID로 사용
            .set(customer)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }
}