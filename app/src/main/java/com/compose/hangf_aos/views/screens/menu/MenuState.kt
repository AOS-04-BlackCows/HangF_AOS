package com.compose.hangf_aos.views.screens.menu

import com.compose.hangf_aos.data.model.Menu

sealed class MenuState {
    data object Idle : MenuState()
    data object Loading : MenuState()
    data class Success(val menu: Menu?) : MenuState()
    data class Error(val message: String) : MenuState()
    data class ListSuccess(val menus: List<Menu>) : MenuState()
    data class Message(val message: String) : MenuState()
}