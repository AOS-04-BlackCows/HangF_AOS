package com.compose.hangf_aos.View.Screens.Customer.Info

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.compose.hangf_aos.View.nevigation.Bookmark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  InfoUI(navController: NavController, modifier: Modifier = Modifier, pageName: String) {
    val (name, setName) = remember { mutableStateOf("") }
    val (phone, setPhone) = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "${pageName}") },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Bookmark.StoreOwnerHome.name)
                    } ) {
                        Icon(
                            Icons.Filled.AccountCircle,
                            contentDescription = "관리자 로그인",
                            tint = Color.Black,
                            modifier = modifier.padding(end = 8.dp)
                        )
                    }
                }
//                navigationIcon = { // 뒤로가기 버튼 - 유저 정보 변경 활성화시 주석 해제
//                    IconButton(onClick = {
////                        navController.navigate(Bookmark.MainHome.name)
//                        Toast.makeText(context,"뒤로가기", Toast.LENGTH_SHORT).show()
//                    }) {//뒤로가기 버튼
//                        Icon(
//                            Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = "ArrowBack",
//                            tint = Color.White,
//                            modifier = modifier.padding(start = 8.dp),
//                        )
//                    }
//                },
            )
        },
        bottomBar = { // 개발 편의를 위한 임시
            Button(onClick = { navController.navigate(Bookmark.CustomerReservation.name) }) {
                Text(text = "이동")
            }
        },
        content = {
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
                    .padding(it)
            ){
                Text(text = "기본 정보를 입력후 [홈 으로] 버튼을 눌러 주세요.")
                Spacer(modifier = modifier.height(10.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { setName(it) },
                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 24.sp),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text
                    ),
                    maxLines = 1,
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                    placeholder = { Text("이름") },
                    label = { Text("이름") }
                )
                Spacer(modifier = modifier.height(10.dp))
                OutlinedTextField(
                    value = phone,
                    onValueChange = { setPhone(it) },
                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 24.sp),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    maxLines = 1,
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                    placeholder = { Text("전화번호") },
                    label = { Text("전화번호") }
                )
                Spacer(modifier = modifier.height(10.dp))
                if (name.isNotEmpty()&&phone.isNotEmpty()){
                    Button(onClick = { navController.navigate(Bookmark.MainHome.name+"/$name,$phone") }) {
                        Text(text = "홈으로")
                    }
                }
            }
        }
    )
}