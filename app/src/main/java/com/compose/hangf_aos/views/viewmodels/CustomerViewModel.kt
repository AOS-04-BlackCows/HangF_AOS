package com.compose.hangf_aos.views.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.hangf_aos.data.model.Customer
import com.compose.hangf_aos.domain.usecase.CustomerUseCase
import com.compose.hangf_aos.views.intents.CustomerIntent
import com.compose.hangf_aos.views.states.CustomerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val customerUseCase: CustomerUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CustomerState>(CustomerState.Idle)
    val state: StateFlow<CustomerState> = _state

    fun handleIntent(intent: CustomerIntent) {
        when (intent) {
            is CustomerIntent.AddCustomer -> addCustomer(intent.customer)
            is CustomerIntent.GetCustomer -> getCustomer(intent.phoneNumber)
            is CustomerIntent.GetAllCustomers -> getAllCustomers()
            is CustomerIntent.UpdateCustomer -> updateCustomer(intent.customer)
            is CustomerIntent.DeleteCustomer -> deleteCustomer(intent.phoneNumber)
            is CustomerIntent.LoadLocalCustomer -> loadLocalCustomer()
            is CustomerIntent.ClearLocalCustomer -> clearLocalCustomer()
            else -> {}
        }
    }

    private fun addCustomer(customer: Customer) {
        viewModelScope.launch {
            _state.value = CustomerState.Loading
            val result = customerUseCase.addCustomer(customer)
            _state.value = if (result.isSuccess) {
                CustomerState.Success(customer)
            } else {
                CustomerState.Error(result.exceptionOrNull()?.message ?: "고객 추가 실패")
            }
        }
    }

    private fun getCustomer(phoneNumber: String) {
        viewModelScope.launch {
            _state.value = CustomerState.Loading
            val result = customerUseCase.getCustomer(phoneNumber)
            _state.value = result.fold(
                onSuccess = { CustomerState.Success(it) },
                onFailure = { CustomerState.Error(it.message ?: "고객 조회 실패") }
            )
        }
    }

    private fun loadLocalCustomer() {
        viewModelScope.launch {
            val result = customerUseCase.getLocalCustomer()
            _state.value = if (result.isSuccess) {
                result.getOrNull()?.let { CustomerState.Success(it) } ?: CustomerState.Idle
            } else {
                CustomerState.Error("로컬 고객 정보 불러오기 실패")
            }
        }
    }

    private fun getAllCustomers() {
        viewModelScope.launch {
            _state.value = CustomerState.Loading
            val result = customerUseCase.getAllCustomers()
            _state.value = result.fold(
                onSuccess = { CustomerState.ListSuccess(it) },
                onFailure = { CustomerState.Error(it.message ?: "전체 고객 조회 실패") }
            )
        }
    }

    private fun updateCustomer(customer: Customer) {
        viewModelScope.launch {
            _state.value = CustomerState.Loading
            val result = customerUseCase.updateCustomer(customer)
            _state.value = if (result.isSuccess) {
                CustomerState.Message("고객 정보가 수정되었습니다.")
            } else {
                CustomerState.Error(result.exceptionOrNull()?.message ?: "고객 수정 실패")
            }
        }
    }

    private fun clearLocalCustomer() {
        viewModelScope.launch {
            customerUseCase.clearLocalCustomer()
        }
    }

    private fun deleteCustomer(phoneNumber: String) {
        viewModelScope.launch {
            _state.value = CustomerState.Loading
            val result = customerUseCase.deleteCustomer(phoneNumber)
            _state.value = if (result.isSuccess) {
                CustomerState.Message("고객 정보가 삭제되었습니다.")
            } else {
                CustomerState.Error(result.exceptionOrNull()?.message ?: "고객 삭제 실패")
            }
        }
    }
}
