package com.compose.hangf_aos.domain.usecase

import com.compose.hangf_aos.data.model.Menu
import com.compose.hangf_aos.data.repository.MenuRepository

class MenuUseCase (
    private val menuRepository: MenuRepository
) {
    suspend fun addMenu(menu: Menu): Result<Unit> {
        return menuRepository.addMenu(menu)
    }
    suspend fun getMenu(menuId: String): Result<Menu?> {
        return menuRepository.getMenu(menuId)
    }
    suspend fun getAllMenus(): Result<List<Menu>> {
        return menuRepository.getAllMenus()
    }
    suspend fun getMenusByStore(storeId: String): Result<List<Menu>> {
        return menuRepository.getMenusByStore(storeId)
    }
    suspend fun updateMenu(menu: Menu): Result<Unit> {
        return menuRepository.updateMenu(menu)
    }
    suspend fun deleteMenu(menuId: String): Result<Unit> {
        return menuRepository.deleteMenu(menuId)
    }
}