package com.compose.hangf_aos

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.compose.hangf_aos.Intent.ApiService
import com.compose.hangf_aos.Intent.DatabaseHelper
import com.compose.hangf_aos.View.nevigation.HangFNavigation
import com.compose.hangf_aos.ui.theme.HangF_AOSTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//private val retrofit = Retrofit.Builder()
//    .baseUrl("http://API_서버_IP:8080/")  // 주의: 실제 배포 시 HTTPS 필수
//    .addConverterFactory(GsonConverterFactory.create())
//    .build()

//val apiService = retrofit.create(ApiService::class.java)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HangF_AOSTheme {
                HangFNavigation(pageName = "유저 정보 입력",modifier = Modifier)
            }
        }

        //DB Test Code
        lifecycleScope.launch(Dispatchers.IO) {
//            DB 직접 연결
//            try {
//                val data = DatabaseHelper.fetchData()
//                withContext(Dispatchers.Main) {
//                    Log.d("DB_TEST", data.toString()) // UI 업데이트
//                }
//
//            } catch (e: Exception) {
//                Log.e("DB_TEST API_ERROR", e.message ?: "Unknown error")
//            }

//
//            try {
//                val response = apiService.getCustomers()
//                if (response.isSuccessful) {
//                    val customers = response.body()
//                    Log.d("DB_TEST API_RESPONSE", customers.toString())
//                    withContext(Dispatchers.Main) {
//                        // UI 업데이트
//                    }
//                }
//            } catch (e: Exception) {
//                Log.e("DB_TEST API_ERROR", e.message ?: "Unknown error")
//            }
        }
    }
}