package com.compose.hangf_aos.views.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.hangf_aos.data.repository.repoInterfaces.SearchRepository
import com.compose.hangf_aos.data.retrofit.SearchResponse
import com.compose.hangf_aos.views.intents.AddressIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
) : ViewModel() {
    // 검색어
    private val _regionSearch = MutableStateFlow<SearchResponse?>(null)
    val regionSearch : StateFlow<SearchResponse?> = _regionSearch



    fun handleIntent(intent: AddressIntent) {
        when (intent) {
            is AddressIntent.SearchAddress -> getRegionSearch(intent.address)
            else -> {}
        }
    }
    fun getRegionSearch(query: String) = viewModelScope.launch {
        val response = searchRepository.requestSearch(query)
//        _regionSearch.emit(searchRepository.requestSearch(query))
    }
}