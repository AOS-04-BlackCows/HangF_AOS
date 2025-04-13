package com.compose.hangf_aos.data.repository.repoInterfaces

import com.compose.hangf_aos.data.model.MenuOrder

interface MenuOrderRepository {
    suspend fun addMenuOrder(menuOrder: MenuOrder): Result<Unit>
    suspend fun getMenuOrder(menuOrderId: String): Result<MenuOrder?>
    suspend fun getAllMenuOrders(): Result<List<MenuOrder>>
    suspend fun getMenuOrdersByTime(): Result<List<MenuOrder>>
    suspend fun getMenuOrdersByStore(storeId: String): Result<List<MenuOrder>>
    suspend fun getMenuOrdersByMenuId(menuId: String): Result<List<MenuOrder>>
    suspend fun updateMenuOrder(menuOrder: MenuOrder): Result<Unit>
    suspend fun deleteMenuOrder(menuOrderId: String): Result<Unit>
}