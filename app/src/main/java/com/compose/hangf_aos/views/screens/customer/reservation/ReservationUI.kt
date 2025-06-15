package com.compose.hangf_aos.views.screens.customer.reservation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.compose.hangf_aos.R
import com.compose.hangf_aos.data.model.Menu
import com.compose.hangf_aos.data.model.MenuOrder
import com.compose.hangf_aos.data.model.Order
import com.compose.hangf_aos.domain.usecase.MenuOrderUseCase
import com.compose.hangf_aos.views.intents.MenuIntent
import com.compose.hangf_aos.views.intents.StoreIntent
import com.compose.hangf_aos.views.nevigation.Bookmark
import com.compose.hangf_aos.views.states.MenuState
import com.compose.hangf_aos.views.states.StoreState
import com.compose.hangf_aos.views.viewmodels.MenuOrderViewModel
import com.compose.hangf_aos.views.viewmodels.MenuViewModel
import com.compose.hangf_aos.views.viewmodels.OrderViewModel
import com.compose.hangf_aos.views.viewmodels.StoreViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationUI(navController: NavController, modifier: Modifier = Modifier, pageName: String) {
    val isExpanded = remember { mutableStateOf(false) }

    val (clicks, setClicks) = remember { mutableStateOf(0) }
    val selectedMenus = remember { mutableStateOf(mutableMapOf<String, Int>()) } // menuId -> count

    val scrollState = rememberLazyListState()

    val menuViewModel: MenuViewModel = hiltViewModel()
    val storeViewModel: StoreViewModel = hiltViewModel()


    val menuState by menuViewModel.state.collectAsState()
    val storeState by storeViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        menuViewModel.handleIntent(MenuIntent.GetMenusByStore(storeId = "힐링쿡 용호동점")) //매장 ID 값
        storeViewModel.handleIntent(StoreIntent.GetStore(storeId = "힐링쿡 용호동점")) //매장 ID 값
    }

    val menus = (menuState as? MenuState.ListSuccess)?.menus ?: emptyList()

    val selectedMenuObjects = menus.associateWith { menu ->
        selectedMenus.value[menu.id] ?: 0
    }.filterValues { it > 0 }

    val store = (storeState as? StoreState.Success)?.store
    val storeData = store?.let {
        listOf(
            "매장 이름" to it.name,
            "전화번호" to it.phoneNumber,
            "주소" to it.address,
            "운영 시간" to it.dayOnTime.joinToString { time -> "${time.week} : ${time.openTime}~${time.closeTime}" }
        )
    } ?: emptyList()

    val totalPrice = selectedMenus.value.entries.sumOf { entry ->
        val menu = menus.find { it.id == entry.key }
        (menu?.price ?: 0) * entry.value
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "${pageName}") },
                navigationIcon = { // 뒤로가기 버튼 - 유저 정보 변경 활성화시 주석 해제
                    IconButton(onClick = {
                        navController.navigate(Bookmark.MainHome.name)
//                        Toast.makeText(context, "뒤로가기", Toast.LENGTH_SHORT).show()
                    }) {//뒤로가기 버튼
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "ArrowBack",
                            tint = Color.White,
                            modifier = modifier.padding(start = 8.dp),
                        )
                    }
                },
                actions = {
                    BadgedBox(
                        badge = {
                            if (clicks >= 1) {//클릭한 수량이 1 이상 일때만 나옴
                                Badge(
                                    containerColor = Color.Red,
                                    contentColor = Color.White,
                                ) {
                                    Text("${clicks}")
                                }
                            }
                        }) {
                        IconButton( onClick = {
                            if (clicks > 0) {
                                // Map<Menu, Int> 형태로 변환
                                val selectedMenuList =
                                    selectedMenuObjects.entries.map { it.key to it.value }

                                // 현재 BackStackEntry의 savedStateHandle에 데이터 저장
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "menus",
                                    selectedMenuList
                                )

                                // totalPrice만 URL 파라미터로 넘김
                                navController.navigate(Bookmark.CustomerConfirmed.name + "?totalPrice=$totalPrice")
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "장바구니",
                                modifier = modifier
                                    .size(32.dp)
                                    .padding(top = 4.dp, end = 4.dp),
                            )
                        }
                    }
                },
            )
        },
        floatingActionButton = {
            var btnText = if (clicks == 0) "메뉴를 선택해 주세요" else "총 가격:${totalPrice}원 👉예약 하기👈"
            Button(
                onClick = {
                    if (clicks > 0) {
                        // Map<Menu, Int> 형태로 변환
                        val selectedMenuList =
                            selectedMenuObjects.entries.map { it.key to it.value }

                        // 현재 BackStackEntry의 savedStateHandle에 데이터 저장
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "menus",
                            selectedMenuList
                        )

                        // totalPrice만 URL 파라미터로 넘김
                        navController.navigate(Bookmark.CustomerConfirmed.name + "?totalPrice=$totalPrice")
                    }
                },
                modifier = modifier.fillMaxWidth(),
                content = { Text(text = btnText, fontSize = 16.sp, fontWeight = FontWeight.Bold) }
            )
        },
        floatingActionButtonPosition = androidx.compose.material3.FabPosition.Center
    ) { padding ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Column {
                // 매장 정보 영역
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .border(
                            border = BorderStroke(0.5.dp, Color(0xFF989898)),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .padding(12.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier.padding(horizontal = 16.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .clickable { isExpanded.value = !isExpanded.value }
                        ) {
                            Text("매장 정보", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Icon(
                                imageVector = if (isExpanded.value) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = "Expand Store Info"
                            )
                        }

                        if (isExpanded.value) {
                            storeData.forEach { (label, value) ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 2.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = label)
                                    Text(text = value)
                                }
                            }
                        }
                    }
                }
                //메뉴 리스트 영역
                Column {
                    LazyColumn(
                        state = scrollState,
                        modifier = modifier
                            .fillMaxHeight()
                            .padding(bottom = 75.dp, top = 16.dp)
                    ) {
                        items(menus) { menu ->
                            val count = selectedMenus.value[menu.id] ?: 0

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(menu.pictureUrl)
                                        .transformations(CircleCropTransformation())
                                        .build(),
                                    contentDescription = "메뉴 이미지",
                                    error = painterResource(R.drawable.blackcow_what),
                                    placeholder = painterResource(R.drawable.blackcow_what),
                                    modifier = Modifier.size(60.dp)
                                )

                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 8.dp)
                                ) {
                                    Text(menu.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                    Text(menu.description, fontSize = 12.sp)
                                    Text(
                                        "${menu.price}원",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(onClick = {
                                        if (count > 0) {
                                            setClicks(clicks - 1)
                                            selectedMenus.value =
                                                selectedMenus.value.toMutableMap().apply {
                                                    this[menu.id] = count - 1
                                                    if (this[menu.id] == 0) remove(menu.id)
                                                }
                                        }
                                    }) {
                                        Icon(Icons.Default.Remove, contentDescription = "수량 줄이기")
                                    }

                                    Text("${count}", modifier = Modifier.padding(horizontal = 8.dp))

                                    IconButton(
                                        onClick = {
                                            // update map
                                            setClicks(clicks + 1)
                                            selectedMenus.value =
                                                selectedMenus.value.toMutableMap().apply {
                                                    this[menu.id] = count + 1
                                                }
                                        }
                                    ) {
                                        Icon(Icons.Default.Add, contentDescription = "수량 늘리기")
                                    }
                                }
                            }
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}