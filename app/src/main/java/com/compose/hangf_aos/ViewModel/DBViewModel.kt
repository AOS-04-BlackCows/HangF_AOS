package com.compose.hangf_aos.ViewModel

import androidx.lifecycle.ViewModel
import com.compose.hangf_aos.Intent.Customer
import com.compose.hangf_aos.Model.CustomerIntent
import com.compose.hangf_aos.Model.CustomerState
import com.compose.hangf_aos.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val repository: FirestoreRepository
) : ViewModel() {

    private val _state = MutableStateFlow<CustomerState>(CustomerState.Idle)
    val state: StateFlow<CustomerState> = _state.asStateFlow()

    fun handleIntent(intent: CustomerIntent) {
        when (intent) {
            is CustomerIntent.AddCustomer -> addCustomer(intent.customer)
        }
    }

    private fun addCustomer(customer: Customer) {
        _state.value = CustomerState.Loading

        repository.addCustomer(customer) { success ->
            _state.value = if (success) {
                CustomerState.Success("고객 추가 성공")
            } else {
                CustomerState.Error("고객 추가 실패")
            }
        }
    }
}
