package com.compose.hangf_aos.views.intents

sealed class AddressIntent {
    data class SearchAddress(val address: String) : AddressIntent()
}