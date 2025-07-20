package com.compose.hangf_aos.data.model

data class DayOnTime(
    val week: String = "",         // 예: "Monday", "Tuesday"
    val openTime: String = "",     // 예: "09:00"
    val closeTime: String = ""     // 예: "18:00"
)