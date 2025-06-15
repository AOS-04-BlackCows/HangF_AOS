package com.compose.hangf_aos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.compose.hangf_aos.data.local.LocalStorage
import com.compose.hangf_aos.views.nevigation.HangFNavigation
import com.compose.hangf_aos.ui.theme.HangF_AOSTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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
                HangFNavigation(modifier = Modifier)
            }
        }

        //DB Test Code
//        lifecycleScope.launch(Dispatchers.IO) {
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
//        }
    }
}