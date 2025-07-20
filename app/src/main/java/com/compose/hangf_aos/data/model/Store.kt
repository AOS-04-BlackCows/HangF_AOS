package com.compose.hangf_aos.data.model

data class Store(
    val id: String = "",  // Firestore의 Document ID
    val name: String = "",
    val address: String = "",
    val phoneNumber: String = "",
    val dayOnTime: List<DayOnTime> = emptyList() // 요일별 시간 목록
)