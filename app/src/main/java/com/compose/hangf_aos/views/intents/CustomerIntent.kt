package com.compose.hangf_aos.views.intents

import com.compose.hangf_aos.data.model.Customer

sealed class CustomerIntent {
    data class AddCustomer(val customer: Customer) : CustomerIntent() // 고객 정보 추가
    data class GetCustomer(val phoneNumber: String) : CustomerIntent() // 고객 정보 조회
    data object GetAllCustomers : CustomerIntent() // 모든 고객 정보 조회
    data class UpdateCustomer(val customer: Customer) : CustomerIntent() // 고객 정보 수정
    data class DeleteCustomer(val phoneNumber: String) : CustomerIntent() // 고객 정보 삭제
    data object LoadLocalCustomer: CustomerIntent() // 로컬 고객 정보
    data object ClearLocalCustomer: CustomerIntent() // 로컬 고객 정보 삭제
}