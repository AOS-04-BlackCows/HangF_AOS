package com.compose.hangf_aos.data.Model

data class Customer(
    val name: String = "",
    val phone: String = "" // Firestore에서 Document ID로 사용
)

