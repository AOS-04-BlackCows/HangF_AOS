package com.compose.hangf_aos.Model

import com.compose.hangf_aos.Intent.Customer

// UI의 상태를 표현하는 데이터 클래스
sealed class CustomerState {
    object Idle : CustomerState()
    object Loading : CustomerState()
    data class Success(val message: String) : CustomerState()
    data class Error(val error: String) : CustomerState()
}