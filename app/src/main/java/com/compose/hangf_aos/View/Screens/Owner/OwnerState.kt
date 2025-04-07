package com.compose.hangf_aos.View.Screens.Owner

import com.compose.hangf_aos.data.model.Owner

sealed class OwnerState {
    data object Idle : OwnerState()
    data object Loading : OwnerState()
    data class Success(val owner: Owner?) : OwnerState()
    data class Message(val message: String) : OwnerState() // 성공 메시지용
    data class Error(val message: String) : OwnerState()
}