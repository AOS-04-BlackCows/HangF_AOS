package com.compose.hangf_aos.data.model

import com.google.gson.Gson

data class Menu(
    val id: String = "",
    val storeId: String = "",
    val name: String = "",
    val pictureUrl: String = "",  // 이미지 URL로 저장
    val description: String = "",
    val price: Int = 0,
    val isActive: Boolean = true
) {
    companion object {
        fun fromJson(json: String): Menu {
            return Gson().fromJson(json, Menu::class.java)
        }
    }
}