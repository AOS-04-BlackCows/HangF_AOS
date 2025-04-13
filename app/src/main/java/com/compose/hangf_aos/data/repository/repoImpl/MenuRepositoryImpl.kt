package com.compose.hangf_aos.data.repository.repoImpl

import com.compose.hangf_aos.data.model.Menu
import com.compose.hangf_aos.data.repository.repoInterfaces.MenuRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MenuRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : MenuRepository {
    private val menusRef = db.collection("menus")

    // 메뉴 추가
    override suspend fun addMenu(menu: Menu): Result<Unit> {
        return try {
            val docRef = if (menu.id.isEmpty()) menusRef.document() else menusRef.document(menu.id)
            docRef.set(menu.copy(id = docRef.id)).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 메뉴 조회
    override suspend fun getMenu(menuId: String): Result<Menu?> {
        return try {
            val snapshot = menusRef.document(menuId).get().await()
            Result.success(snapshot.toObject(Menu::class.java))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 특정 매장의 메뉴 목록 조회
    override suspend fun getMenusByStore(storeId: String): Result<List<Menu>> {
        return try {
            val snapshot = menusRef.whereEqualTo("storeId", storeId).get().await()
            val menus = snapshot.documents.mapNotNull { it.toObject(Menu::class.java) }
            Result.success(menus)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 모든 메뉴 조회
    override suspend fun getAllMenus(): Result<List<Menu>> {
        return try {
            val snapshot = menusRef.get().await()
            val menus = snapshot.documents.mapNotNull { it.toObject(Menu::class.java) }
            Result.success(menus)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 등록 시간 순으로 정렬된 메뉴 목록 조회
    override suspend fun getMenusByTime(): Result<List<Menu>> {
        return try {
            val snapshot = menusRef.orderBy("timestamp").get().await()
            val menus = snapshot.documents.mapNotNull { it.toObject(Menu::class.java) }
            Result.success(menus)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 메뉴 수정
    override suspend fun updateMenu(menu: Menu): Result<Unit> {
        return try {
            menusRef.document(menu.id).set(menu).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 메뉴 삭제
    override suspend fun deleteMenu(menuId: String): Result<Unit> {
        return try {
            menusRef.document(menuId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
