package com.compose.hangf_aos.data.model

// 고객 정보
data class Customer(
    val name: String = "",
    val phone: String = "" // Firestore에서 Document ID로 사용
)
