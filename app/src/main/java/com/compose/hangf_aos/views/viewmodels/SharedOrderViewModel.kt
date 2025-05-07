package com.compose.hangf_aos.views.viewmodels

import androidx.lifecycle.ViewModel
import com.compose.hangf_aos.data.model.Menu
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SharedOrderViewModel @Inject constructor() : ViewModel() {
    private val _selectedMenus = MutableStateFlow<Map<Menu, Int>>(emptyMap())
    val selectedMenus: StateFlow<Map<Menu, Int>> = _selectedMenus

    private val _totalPrice = MutableStateFlow(0)
    val totalPrice: StateFlow<Int> = _totalPrice

    fun setSelectedMenus(menus: Map<Menu, Int>) {
        _selectedMenus.value = menus
    }

    fun setTotalPrice(price: Int) {
        _totalPrice.value = price
    }
}
