package com.compose.hangf_aos.views.screens.owner

import com.compose.hangf_aos.data.model.Owner

sealed class OwnerIntent {
    data class AddOwner(val owner: Owner) : OwnerIntent()
    data class GetOwner(val ownerId: String) : OwnerIntent()
    data class UpdateOwner(val owner: Owner) : OwnerIntent()
    data class DeleteOwner(val ownerId: String) : OwnerIntent()
}