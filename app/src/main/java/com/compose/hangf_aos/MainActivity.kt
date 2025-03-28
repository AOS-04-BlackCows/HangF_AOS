package com.compose.hangf_aos

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.compose.hangf_aos.Intent.DatabaseHelper
import com.compose.hangf_aos.View.nevigation.HangFNavigation
import com.compose.hangf_aos.ui.theme.HangF_AOSTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            val data = DatabaseHelper.fetchData()
            withContext(Dispatchers.Main) {
                Log.d("DB_TEST", data.toString()) // UI 업데이트
            }
        }

    }
}