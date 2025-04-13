package com.compose.hangf_aos.views.screens.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.hangf_aos.data.model.Store
import com.compose.hangf_aos.domain.usecase.StoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val storeUseCase: StoreUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<StoreState>(StoreState.Idle)
    val state: StateFlow<StoreState> = _state

    fun handleIntent(intent: StoreIntent) {
        when (intent) {
            is StoreIntent.AddStore -> addStore(intent.store)
            is StoreIntent.GetStore -> getStore(intent.storeId)
            is StoreIntent.UpdateStore -> updateStore(intent.store)
            is StoreIntent.DeleteStore -> deleteStore(intent.storeId)
        }
    }

    private fun addStore(store: Store) {
        viewModelScope.launch {
            _state.value = StoreState.Loading
            val result = storeUseCase.addStore(store)
            _state.value = result.fold(
                onSuccess = { StoreState.Success(store) },
                onFailure = { StoreState.Error("스토어 추가 실패") }
            )
        }
    }

    private fun getStore(id: String) {
        viewModelScope.launch {
            _state.value = StoreState.Loading
            val result = storeUseCase.getStore(id)
            _state.value = result.fold(
                onSuccess = { StoreState.Success(it) },
                onFailure = { StoreState.Error("스토어 조회 실패") }
            )
        }
    }

    private fun updateStore(store: Store) {
        viewModelScope.launch {
            _state.value = StoreState.Loading
            val result = storeUseCase.updateStore(store)
            _state.value = if (result.isSuccess) {
                StoreState.Success(store)
            } else {
                StoreState.Error("스토어 수정 실패")
            }
        }
    }

    private fun deleteStore(id: String) {
        viewModelScope.launch {
            _state.value = StoreState.Loading
            val result = storeUseCase.deleteStore(id)
            _state.value = if (result.isSuccess) {
                StoreState.Success(null)
            } else {
                StoreState.Error("스토어 삭제 실패")
            }
        }
    }
}