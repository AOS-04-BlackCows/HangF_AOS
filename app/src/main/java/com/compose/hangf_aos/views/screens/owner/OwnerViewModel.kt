package com.compose.hangf_aos.views.screens.owner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.hangf_aos.data.model.Owner
import com.compose.hangf_aos.domain.usecase.OwnerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OwnerViewModel @Inject constructor(
    private val OwnerUseCase: OwnerUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<OwnerState>(OwnerState.Idle)
    val state: StateFlow<OwnerState> = _state

    fun handleIntent(intent: OwnerIntent) {
        when (intent) {
            is OwnerIntent.AddOwner -> addOwner(intent.owner)
            is OwnerIntent.GetOwner -> getOwner(intent.ownerId)
            is OwnerIntent.UpdateOwner -> updateOwner(intent.owner)
            is OwnerIntent.DeleteOwner -> deleteOwner(intent.ownerId)
        }
    }

    private fun addOwner(Owner: Owner) {
        viewModelScope.launch {
            _state.value = OwnerState.Loading
            val result = OwnerUseCase.addOwner(Owner)
            _state.value = if (result.isSuccess) {
                OwnerState.Success(Owner)
            } else {
                OwnerState.Error(result.exceptionOrNull()?.message ?: "점주 추가 실패")
            }
        }
    }

    private fun getOwner(phoneNumber: String) {
        viewModelScope.launch {
            _state.value = OwnerState.Loading
            val result = OwnerUseCase.getOwner(phoneNumber)
            _state.value = result.fold(
                onSuccess = { OwnerState.Success(it) },
                onFailure = { OwnerState.Error(it.message ?: "점주 조회 실패") }
            )
        }
    }

    private fun updateOwner(Owner: Owner) {
        viewModelScope.launch {
            _state.value = OwnerState.Loading
            val result = OwnerUseCase.updateOwner(Owner)
            _state.value = if (result.isSuccess) {
                OwnerState.Message("점주 정보가 수정되었습니다.")
            } else {
                OwnerState.Error(result.exceptionOrNull()?.message ?: "점주 수정 실패")
            }
        }
    }

    private fun deleteOwner(phoneNumber: String) {
        viewModelScope.launch {
            _state.value = OwnerState.Loading
            val result = OwnerUseCase.deleteOwner(phoneNumber)
            _state.value = if (result.isSuccess) {
                OwnerState.Message("점주 정보가 삭제되었습니다.")
            } else {
                OwnerState.Error(result.exceptionOrNull()?.message ?: "점주 삭제 실패")
            }
        }
    }
}
