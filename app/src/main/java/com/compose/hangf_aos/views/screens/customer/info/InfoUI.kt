package com.compose.hangf_aos.views.screens.customer.info

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle

//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.filled.AccountCircle

import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.compose.hangf_aos.views.intents.CustomerIntent
import com.compose.hangf_aos.views.states.CustomerState
import com.compose.hangf_aos.views.nevigation.Bookmark
import com.compose.hangf_aos.data.model.Customer
import com.compose.hangf_aos.views.viewmodels.CustomerViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  InfoUI(viewModel: CustomerViewModel = hiltViewModel(), navController: NavController, modifier: Modifier = Modifier, pageName: String) {
    val (name, setName) = remember { mutableStateOf("") }
    val (phone, setPhone) = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    val state by viewModel.state.collectAsState()



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

                //DB 테스트 코드 시작점
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val customer = Customer(name, phone)
                        viewModel.handleIntent(CustomerIntent.AddCustomer(customer))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("고객 추가")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        viewModel.handleIntent(CustomerIntent.GetAllCustomers)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("고객 조회")
                }

                when (state) {
                    is CustomerState.Loading -> CircularProgressIndicator()
                    is CustomerState.Success -> {
                        val customer = (state as CustomerState.Success).customer
                        customer?.let {
                            Text("이름: ${it.name}, 전화번호: ${it.phone}")
                        } ?: Text("고객 정보 없음")
                    }
                    is CustomerState.ListSuccess -> {
                        val customers = (state as CustomerState.ListSuccess).customers
                        customers.forEach {
                            Text("이름: ${it.name}, 전화번호: ${it.phone}")
                        }
                    }
                    is CustomerState.Error -> Toast.makeText(context, (state as CustomerState.Error).message, Toast.LENGTH_SHORT).show()
                    else -> {}
                }
                //DB 테스트 코드 끝점

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