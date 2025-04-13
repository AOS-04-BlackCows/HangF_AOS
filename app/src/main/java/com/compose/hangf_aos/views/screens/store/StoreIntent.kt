package com.compose.hangf_aos.views.screens.store

import com.compose.hangf_aos.data.model.Store

sealed class StoreIntent {
    data class AddStore(val store: Store) : StoreIntent()
    data class GetStore(val storeId: String) : StoreIntent()
    data class UpdateStore(val store: Store) : StoreIntent()
    data class DeleteStore(val storeId: String) : StoreIntent()
}