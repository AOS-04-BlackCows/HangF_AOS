package com.compose.hangf_aos.data.repository.repoInterfaces

import com.compose.hangf_aos.data.model.Menu

interface MenuRepository {
    suspend fun addMenu(menu: Menu): Result<Unit>
    suspend fun getMenu(menuId: String): Result<Menu?>
    suspend fun getAllMenus(): Result<List<Menu>>
    suspend fun getMenusByStore(storeId: String): Result<List<Menu>>
    suspend fun getMenusByTime(): Result<List<Menu>>
    suspend fun updateMenu(menu: Menu): Result<Unit>
    suspend fun deleteMenu(menuId: String): Result<Unit>
}