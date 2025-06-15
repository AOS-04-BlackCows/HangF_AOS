package com.compose.hangf_aos.data.model

import com.google.gson.Gson

// 공통 타입 정의
enum class OrderStatus {
    Pending, Accepted, Complete, Rejected, Cancelled
}

data class DayOnTime(
    val week: String = "",         // 예: "Monday", "Tuesday"
    val openTime: String = "",     // 예: "09:00"
    val closeTime: String = ""     // 예: "18:00"
)

// 고객 정보
data class Customer(
    val name: String = "",
    val phone: String = "" // Firestore에서 Document ID로 사용
)

data class Owner(
    val id: String = "",                 // Firestore에서 auto-id로 대체
    val storeId: String = "",
    val name: String = "",
    val loginId: String = "",
    val password: String = "",
    val phoneNumber: String = ""
)

data class Store(
    val id: String = "",  // Firestore의 Document ID
    val name: String = "",
    val address: String = "",
    val phoneNumber: String = "",
    val dayOnTime: List<DayOnTime> = emptyList() // 요일별 시간 목록
)

data class Menu(
    val id: String = "",
    val storeId: String = "",
    val name: String = "",
    val pictureUrl: String = "",  // 이미지 URL로 저장
    val description: String = "",
    val price: Int = 0,
    val isActive: Boolean = true
) {
    companion object {
        fun fromJson(json: String): Menu {
            return Gson().fromJson(json, Menu::class.java)
        }
    }
}

data class MenuOrder(
    val id: String = "",
    val menuId: String = "",
    val orderId: String = "",
    val num: Int = 0,
    val totalPrice: Int = 0
)

data class Order(
    val id: String = "",
    val storeId: String = "",
    val customerId: String = "",
    val customerName: String = "",
    val userPhoneNumber: String = "",
    val menuOrders: List<String> = emptyList(), // MenuOrder ID 리스트
    val totalPrice: Int = 0,
    val status: OrderStatus = OrderStatus.Pending,
    val pickUpTime: String = "" // LocalDateTime을 ISO String으로 변환해 저장
)