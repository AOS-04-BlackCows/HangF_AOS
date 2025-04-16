package com.compose.hangf_aos.views.states

import com.compose.hangf_aos.data.model.Store

sealed class StoreState {
    data object Idle : StoreState()
    data object Loading : StoreState()
    data class Success(val store: Store?) : StoreState()
    data class Error(val message: String) : StoreState()
}