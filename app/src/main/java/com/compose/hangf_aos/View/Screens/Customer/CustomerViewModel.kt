package com.compose.hangf_aos.View.Screens.Customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.hangf_aos.data.Model.Customer
import com.compose.hangf_aos.domain.usecase.AddCustomerUseCase
import com.compose.hangf_aos.domain.usecase.GetCustomerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CustomerViewModel(
    private val addCustomerUseCase: AddCustomerUseCase,
    private val getCustomerUseCase: GetCustomerUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CustomerState>(CustomerState.Idle)
    val state: StateFlow<CustomerState> = _state

    fun handleIntent(intent: CustomerIntent) {
        when (intent) {
            is CustomerIntent.AddCustomer -> addCustomer(intent.customer)
            is CustomerIntent.GetCustomer -> getCustomer(intent.phoneNumber)
        }
    }

    private fun addCustomer(customer: Customer) {
        viewModelScope.launch {
            _state.value = CustomerState.Loading
            val result = addCustomerUseCase(customer)
            _state.value = if (result.isSuccess) {
                CustomerState.Success(customer)
            } else {
                CustomerState.Error("고객 추가 실패")
            }
        }
    }

    private fun getCustomer(phoneNumber: String) {
        viewModelScope.launch {
            _state.value = CustomerState.Loading
            val result = getCustomerUseCase(phoneNumber)
            _state.value = result.fold(
                onSuccess = { CustomerState.Success(it) },
                onFailure = { CustomerState.Error("고객 조회 실패") }
            )
        }
    }
}