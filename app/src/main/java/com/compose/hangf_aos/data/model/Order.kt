package com.compose.hangf_aos.data.model

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