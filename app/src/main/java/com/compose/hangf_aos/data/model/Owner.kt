package com.compose.hangf_aos.data.model

data class Owner(
    val id: String = "",                 // Firestore에서 auto-id로 대체
    val storeId: String = "",
    val name: String = "",
    val loginId: String = "",
    val password: String = "",
    val phoneNumber: String = ""
)