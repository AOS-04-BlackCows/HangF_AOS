package com.compose.hangf_aos.View.Screens.MenuOrder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.hangf_aos.data.model.MenuOrder
import com.compose.hangf_aos.domain.usecase.MenuOrderUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MenuOrderViewModel (
    private val menuOrderUseCase: MenuOrderUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<MenuOrderState>(MenuOrderState.Idle)
    val state: StateFlow<MenuOrderState> = _state

    fun handleIntent(intent: MenuOrderIntent) {
        when (intent) {
            is MenuOrderIntent.AddMenuOrder -> addMenuOrder(intent.menuOrder)
            is MenuOrderIntent.GetMenuOrder -> getMenuOrder(intent.id)
            is MenuOrderIntent.GetMenuOrdersByStore -> getMenuOrdersByStore(intent.storeId)
            is MenuOrderIntent.GetAllMenuOrders -> getAllMenuOrders()
            is MenuOrderIntent.UpdateMenuOrder -> updateMenuOrder(intent.menuOrder)
            is MenuOrderIntent.DeleteMenuOrder -> deleteMenuOrder(intent.id)
        }
    }

    private fun addMenuOrder(menuOrder: MenuOrder) {
        viewModelScope.launch {
            _state.value = MenuOrderState.Loading
            val result = menuOrderUseCase.addMenuOrder(menuOrder)
            _state.value = if (result.isSuccess) {
                MenuOrderState.Success(menuOrder)
            } else {
                MenuOrderState.Error(result.exceptionOrNull()?.message ?: "메뉴 주문 추가 실패")
            }
        }
    }

    private fun getMenuOrder(id: Int) {
        viewModelScope.launch {
            _state.value = MenuOrderState.Loading
            val result = menuOrderUseCase.getMenuOrder(id)
            _state.value = result.fold(
                onSuccess = { MenuOrderState.Success(it) },
                onFailure = { MenuOrderState.Error(it.message ?: "메뉴 주문 조회 실패") }
            )
        }
    }

    private fun getMenuOrdersByStore(storeId: Int) {
        viewModelScope.launch {
            _state.value = MenuOrderState.Loading
            val result = menuOrderUseCase.getMenuOrdersByStore(storeId)
            _state.value = result.fold(
                onSuccess = { MenuOrderState.Success(it) },
                onFailure = { MenuOrderState.Error(it.message ?: "특정 가게의 메뉴 주문 조회 실패") }
            )
        }
    }

    private fun getAllMenuOrders() {
        viewModelScope.launch {
            _state.value = MenuOrderState.Loading
            val result = menuOrderUseCase.getAllMenuOrders()
            _state.value = result.fold(
                onSuccess = { MenuOrderState.Success(it) },
                onFailure = { MenuOrderState.Error(it.message ?: "전체 메뉴 주문 조회 실패") }
            )
        }
    }

    private fun updateMenuOrder(menuOrder: MenuOrder) {
        viewModelScope.launch {
            _state.value = MenuOrderState.Loading
            val result = menuOrderUseCase.updateMenuOrder(menuOrder)
            _state.value = if (result.isSuccess) {
                MenuOrderState.Success(menuOrder)
            } else {
                MenuOrderState.Error(result.exceptionOrNull()?.message ?: "메뉴 주문 수정 실패")
            }
        }
    }

    private fun deleteMenuOrder(id: Int) {
        viewModelScope.launch {
            _state.value = MenuOrderState.Loading
            val result = menuOrderUseCase.deleteMenuOrder(id)
            _state.value = if (result.isSuccess) {
                MenuOrderState.Success(id)
            } else {
                MenuOrderState.Error(result.exceptionOrNull()?.message ?: "메뉴 주문 삭제 실패")
            }

        }
    }
}