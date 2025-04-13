package com.compose.hangf_aos.View.Screens.MenuOrder

import com.compose.hangf_aos.data.model.MenuOrder

sealed class MenuOrderIntent {
    data class AddMenuOrder(val menuOrder: MenuOrder) : MenuOrderIntent()
    data class GetMenuOrder(val id: Int) : MenuOrderIntent()
    data object GetAllMenuOrders : MenuOrderIntent()
    data class GetMenuOrdersByStore(val storeId: Int) : MenuOrderIntent()
    data class UpdateMenuOrder(val menuOrder: MenuOrder) : MenuOrderIntent()
    data class DeleteMenuOrder(val id: Int) : MenuOrderIntent()
}