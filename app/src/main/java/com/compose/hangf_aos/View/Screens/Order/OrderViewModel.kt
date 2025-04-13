package com.compose.hangf_aos.View.Screens.Order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.hangf_aos.data.model.Order
import com.compose.hangf_aos.domain.usecase.OrderUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderViewModel (
    private val orderUseCase: OrderUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<OrderState>(OrderState.Idle)
    val state: StateFlow<OrderState> = _state

    fun handleIntent(intent: OrderIntent , order: Order?) {
        when (intent) {
            is OrderIntent.AddOrder -> addOrder(intent.order)
            is OrderIntent.GetOrder -> getOrder(intent.orderId)
            is OrderIntent.GetAllOrders -> getAllOrders()
            is OrderIntent.GetOrdersByTime -> getOrdersByTime()
            is OrderIntent.GetOrdersByStatus -> if (order != null) {
                getOrdersByStatus(order.status.toString())
            }
            is OrderIntent.GetOrdersByCustomer -> if (order != null) {
                getOrdersByCustomer(order.customerId)
            }
            is OrderIntent.GetOrdersByStore -> if (order != null) {
                getOrdersByStore(order.storeId)
            }
            is OrderIntent.UpdateOrder -> updateOrder(intent.order)
            is OrderIntent.DeleteOrder -> deleteOrder(intent.orderId)
        }
    }

    private fun addOrder(order: Order) {
        viewModelScope.launch {
            _state.value = OrderState.Loading
            val result = orderUseCase.addOrder(order)
            _state.value = result.fold(
                onSuccess = { OrderState.Success(order) },
                onFailure = { OrderState.Error("주문 추가 실패") }
            )
        }
    }

    private fun getOrder(id: String) {
        viewModelScope.launch {
            _state.value = OrderState.Loading
            val result = orderUseCase.getOrder(id)
            _state.value = result.fold(
                onSuccess = { OrderState.Success(it) },
                onFailure = { OrderState.Error("주문 조회 실패") }
            )
        }
    }

    private fun getAllOrders() {
        viewModelScope.launch {
            _state.value = OrderState.Loading
            val result = orderUseCase.getAllOrders()
            _state.value = result.fold(
                onSuccess = { OrderState.ListSuccess(it) },
                onFailure = { OrderState.Error("모든 주문 조회 실패") }
            )
        }
    }

    private fun getOrdersByTime() {
        viewModelScope.launch {
            _state.value = OrderState.Loading
            val result = orderUseCase.getOrdersByTime()
            _state.value = result.fold(
                onSuccess = { OrderState.ListSuccess(it) },
                onFailure = { OrderState.Error("주문 시간별 조회 실패") }
            )
        }
    }

    private fun getOrdersByStatus(status: String) {
        viewModelScope.launch {
            _state.value = OrderState.Loading
            val result = orderUseCase.getOrdersByStatus(status)
            _state.value = result.fold(
                onSuccess = { OrderState.ListSuccess(it) },
                onFailure = { OrderState.Error("주문 상태별 조회 실패") }
            )
        }
    }

    private fun getOrdersByCustomer(customerId: String) {
        viewModelScope.launch {
            _state.value = OrderState.Loading
            val result = orderUseCase.getOrdersByCustomer(customerId)
            _state.value = result.fold(
                onSuccess = { OrderState.ListSuccess(it) },
                onFailure = { OrderState.Error("고객별 주문 조회 실패") }
            )
        }
    }

    private fun getOrdersByStore(storeId: String) {
        viewModelScope.launch {
            _state.value = OrderState.Loading
            val result = orderUseCase.getOrdersByStore(storeId)
            _state.value = result.fold(
                onSuccess = { OrderState.ListSuccess(it) },
                onFailure = { OrderState.Error("가게별 주문 조회 실패") }
            )
        }
    }

    private fun updateOrder(order: Order) {
        viewModelScope.launch {
            _state.value = OrderState.Loading
            val result = orderUseCase.updateOrder(order)
            _state.value = if (result.isSuccess) {
                OrderState.Success(order)
            } else {
                OrderState.Error("주문 수정 실패")
            }
        }
    }

    private fun deleteOrder(id: String) {
        viewModelScope.launch {
            _state.value = OrderState.Loading
            val result = orderUseCase.deleteOrder(id)
            _state.value = if (result.isSuccess) {
                OrderState.Success(null)
            } else {
                OrderState.Error("주문 삭제 실패")
            }
        }
    }
}