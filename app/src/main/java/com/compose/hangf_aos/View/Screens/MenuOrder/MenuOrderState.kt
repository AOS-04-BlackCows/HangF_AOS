package com.compose.hangf_aos.View.Screens.MenuOrder

sealed class MenuOrderState {
    data object Idle : MenuOrderState()
    data object Loading : MenuOrderState()
    data class Success(val data: Any?) : MenuOrderState()
    data class Error(val message: String) : MenuOrderState()
}