package com.compose.hangf_aos.Model

import com.compose.hangf_aos.Intent.Customer

// UI에서 발생할 수 있는 이벤트 정의
sealed class CustomerIntent {
    data class AddCustomer(val customer: Customer) : CustomerIntent()
}