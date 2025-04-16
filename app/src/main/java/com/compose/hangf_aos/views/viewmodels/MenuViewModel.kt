package com.compose.hangf_aos.views.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.hangf_aos.data.model.Menu
import com.compose.hangf_aos.domain.usecase.MenuUseCase
import com.compose.hangf_aos.views.intents.MenuIntent
import com.compose.hangf_aos.views.states.MenuState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val menuUseCase: MenuUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<MenuState>(MenuState.Idle)
    val state: StateFlow<MenuState> = _state

    fun handleIntent(intent: MenuIntent) {
        when (intent) {
            is MenuIntent.AddMenu -> addMenu(intent.menu)
            is MenuIntent.GetMenu -> getMenu(intent.menuId)
            is MenuIntent.GetAllMenus -> getAllMenus()
            is MenuIntent.GetMenusByStore -> getMenusByStore(intent.storeId)
            is MenuIntent.UpdateMenu -> updateMenu(intent.menu)
            is MenuIntent.DeleteMenu -> deleteMenu(intent.menuId)
        }
    }

    private fun addMenu(menu: Menu) {
        viewModelScope.launch {
            _state.value = MenuState.Loading
            val result = menuUseCase.addMenu(menu)
            _state.value = result.fold(
                onSuccess = { MenuState.Success(menu) },
                onFailure = { MenuState.Error("메뉴 추가 실패") }
            )
        }
    }

    private fun getMenu(id: String) {
        viewModelScope.launch {
            _state.value = MenuState.Loading
            val result = menuUseCase.getMenu(id)
            _state.value = result.fold(
                onSuccess = { MenuState.Success(it) },
                onFailure = { MenuState.Error("메뉴 조회 실패") }
            )
        }
    }

    private fun getAllMenus() {
        viewModelScope.launch {
            _state.value = MenuState.Loading
            val result = menuUseCase.getAllMenus()
            _state.value = result.fold(
                onSuccess = { MenuState.ListSuccess(it) },
                onFailure = { MenuState.Error("메뉴 목록 조회 실패") }
            )
        }
    }

    private fun getMenusByStore(storeId: String){
        viewModelScope.launch {
            _state.value = MenuState.Loading
            val result = menuUseCase.getMenusByStore(storeId)
            _state.value = result.fold(
                onSuccess = { MenuState.ListSuccess(it) },
                onFailure = { MenuState.Error("메뉴 목록 조회 실패") }
            )
        }
    }

    private fun updateMenu(menu: Menu){
        viewModelScope.launch {
            _state.value = MenuState.Loading
            val result = menuUseCase.updateMenu(menu)
            _state.value = if (result.isSuccess) {
                MenuState.Success(menu)
            } else {
                MenuState.Error("메뉴 수정 실패")
            }
        }
    }

    private fun deleteMenu(id: String){
        viewModelScope.launch {
            _state.value = MenuState.Loading
            val result = menuUseCase.deleteMenu(id)
            _state.value = if (result.isSuccess) {
                MenuState.Success(null)
            } else {
                MenuState.Error("메뉴 삭제 실패")
            }
        }
    }
}