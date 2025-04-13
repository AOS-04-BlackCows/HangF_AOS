package com.compose.hangf_aos.domain.usecase

import com.compose.hangf_aos.data.model.MenuOrder
import com.compose.hangf_aos.data.repository.repoInterfaces.MenuOrderRepository
import javax.inject.Inject

class MenuOrderUseCase @Inject constructor(
    private val menuOrderRepository: MenuOrderRepository
) {
    suspend fun addMenuOrder(menuOrder: MenuOrder) : Result<Unit> {
        return menuOrderRepository.addMenuOrder(menuOrder)
    }
    suspend fun getMenuOrder(id: Int) : Result<MenuOrder?> {
        return menuOrderRepository.getMenuOrder(id.toString())
    }
    suspend fun getAllMenuOrders() : Result<List<MenuOrder>> {
        return menuOrderRepository.getAllMenuOrders()
    }
    suspend fun getMenuOrdersByStore(storeId: Int) : Result<List<MenuOrder>> {
        return menuOrderRepository.getMenuOrdersByStore(storeId.toString())
    }
    suspend fun updateMenuOrder(menuOrder: MenuOrder) : Result<Unit> {
        return menuOrderRepository.updateMenuOrder(menuOrder)
    }
    suspend fun deleteMenuOrder(id: Int) : Result<Unit> {
        return menuOrderRepository.deleteMenuOrder(id.toString())
    }
}