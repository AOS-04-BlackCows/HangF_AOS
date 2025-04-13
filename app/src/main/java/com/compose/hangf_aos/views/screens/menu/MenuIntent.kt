package com.compose.hangf_aos.views.screens.menu

import com.compose.hangf_aos.data.model.Menu

sealed class MenuIntent {
    data class AddMenu(val menu: Menu) : MenuIntent()
    data class GetMenu(val menuId: String) : MenuIntent()
    data class GetAllMenus(val storeId: String) : MenuIntent()
    data class GetMenusByStore(val storeId: String) : MenuIntent()
    data class UpdateMenu(val menu: Menu) : MenuIntent()
    data class DeleteMenu(val menuId: String) : MenuIntent()
}