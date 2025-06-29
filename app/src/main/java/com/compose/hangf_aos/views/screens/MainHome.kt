package com.compose.hangf_aos.views.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.compose.hangf_aos.R
import com.compose.hangf_aos.views.intents.CustomerIntent
import com.compose.hangf_aos.views.nevigation.Bookmark
import com.compose.hangf_aos.views.viewmodels.CustomerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainHome(
    viewModel: CustomerViewModel = hiltViewModel(),
    navController: NavController,
    modifier: Modifier = Modifier,
    pageName: String
) {
    val context = LocalContext.current

    val showStoreDialog = remember { mutableStateOf(false) }
    val showMenuDialog = remember { mutableStateOf(false) }
    val showAddressDialog = remember { mutableStateOf(false) }

    val selectedAddress = remember { mutableStateOf("") } // 선택된 주소 상태

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "${pageName}") },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Bookmark.StoreOwnerHome.name)
                        Toast.makeText(context, "관리자 아이콘 클릭됨", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            Icons.Filled.AccountCircle,
                            contentDescription = "관리자 로그인",
                            tint = Color.Black,
                            modifier = modifier.padding(end = 8.dp)
                        )
                    }
                }
            )
        },
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
                    .padding(it)
            ) {

            }
        }
    )
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)//coil 방식
                //추후 서버에 가맹점 로고 활성화시 주석 해제 및 링크 연동 예정
//                .data("https://cdn.pixabay.com/photo/2024/09/08/20/30/architecture-9033164_1280.jpg")
                .data(R.drawable.blackcow_logo)
                .crossfade(1000)//정해진 시간 동안 이미지를 천천히 띄워줌
//                .transformations(CircleCropTransformation()) //이미지 변형
                .build(),
            contentDescription = "가멩정 로고",
            error = painterResource(R.drawable.blackcow_wow),//애러 떳을 때 이미지 띄워줌
            placeholder = painterResource(R.drawable.blackcow_what),//이미지 로드 전에 띄워줄 이미지
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = modifier.height(20.dp))

        Spacer(modifier = modifier.height(20.dp))

        Button(onClick = { navController.navigate(Bookmark.CustomerReservation.name) }) {
            Text(text = "예약 화면")
        }
        Button(onClick = { navController.navigate(Bookmark.CustomerLookup.name) }) {
            Text(text = "조회 화면")
        }
        Text(text = "테스트용 버튼" , color = Color.White)
        Box (
            modifier = modifier
                .fillMaxWidth()
                .border(
                    border = BorderStroke(0.5.dp, Color(0xFF989898)),
                    shape = RoundedCornerShape(5.dp)
                )
                .padding(12.dp)
        ){
            Column {
                Row {
                    Button(onClick = { showStoreDialog.value = true }) {
                        Text(text = "스토어 정보 저장")
                    }
                    Button(onClick = { showMenuDialog.value = true }) {
                        Text(text = "메뉴 정보 저장")
                    }
                }
                Row {
                    Button(onClick = { showAddressDialog.value = true }) {
                        Text(text = "주소 검색")
                    }
                    Text(text = "주소: ${selectedAddress.value}", color = Color.White)
                }
                Row {
                    Button(onClick = {
                        viewModel.handleIntent(CustomerIntent.ClearLocalCustomer)
                        navController.navigate(Bookmark.CustomerInfo.name)
                    }) {
                        Text("로그아웃")
                    }
                }
            }
        }
    }
    if (showStoreDialog.value) {
        T_StoreInfoDialog(
            onDismiss = {
                showStoreDialog.value = false
            }
        )
    }
    if (showMenuDialog.value) {
        T_MenuEditDialog(
            onDismiss = {
                showMenuDialog.value = false
            }
        )
    }
    if (showAddressDialog.value){
        T_AddressDialog(
            onDismiss = { showAddressDialog.value = false },
            onAddressSelected = { address ->
                selectedAddress.value = address // 주소 선택 시 상태 변경
                showAddressDialog.value = false // 다이얼로그 닫기
            }
        )
    }
}