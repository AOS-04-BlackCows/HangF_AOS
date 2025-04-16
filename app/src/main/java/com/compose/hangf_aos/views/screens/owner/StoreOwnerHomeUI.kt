package com.compose.hangf_aos.views.screens.owner

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.FlowRow

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun StoreOwnerHomeUI(navController: NavController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val showFabMenu = remember { mutableStateOf(false) }

    // 예약 요청 다이얼로그
    val showDialog = remember { mutableStateOf(false) }
    val selectedReservation = remember { mutableStateOf<String?>(null) }
    val showStoreDialog = remember { mutableStateOf(false) }
    val dialogType = remember { mutableStateOf<String?>(null) }

    // 모두보기를 눌렀는지 여부 기억하는 상태 변수
    val showAllRequests = remember { mutableStateOf(false) }
    val reservationRequests = listOf(
        "김철수(1234)", "이영희(5678)", "박민수(8910)",
        "최민정(1122)", "한상우(3344)", "정은지(5566)",
        "류지혁(7788)", "오지현(9900)"
    )
    val reservationList = listOf(
        "김철수(1234) - 김치, 불고기, 계란말이, 잡채, 나물, 고등어조림",
        "이영희(5678) - 잡채, 된장찌개, 계란찜, 제육볶음, 오징어볶음, 깻잎무침",
        "박민수(8910) - 계란말이, 불고기, 김치찌개, 고사리, 미역줄기볶음",
        "김철수(1234) - 김치, 불고기, 계란말이, 잡채, 나물, 고등어조림",
        "이영희(5678) - 잡채, 된장찌개, 계란찜, 제육볶음, 오징어볶음, 깻잎무침",
        "박민수(8910) - 계란말이, 불고기, 김치찌개, 고사리, 미역줄기볶음",
        "김철수(1234) - 김치, 불고기, 계란말이, 잡채, 나물, 고등어조림",
        "이영희(5678) - 잡채, 된장찌개, 계란찜, 제육볶음, 오징어볶음, 깻잎무침",
    )
    val selected = selectedReservation.value

    // 기본  6개만 보여주고, 버튼 누르면 전체 보여줌
    val visibleRequests =
        if (showAllRequests.value) reservationRequests else reservationRequests.take(6)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("점주 모드", fontSize = 18.sp, fontWeight = FontWeight.Bold) }
            )
        },

        // 플로팅 액션 버튼
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End) {
                if (showFabMenu.value) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        tonalElevation = 4.dp,
                        shadowElevation = 8.dp,
                        modifier = modifier.width(150.dp)
                    ) {
                        Column {
                            Text(
                                "매장정보 변경",
                                fontSize = 15.sp,
                                modifier = modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        showFabMenu.value = false
                                        showStoreDialog.value = true
                                    }
                                    .padding(vertical = 16.dp, horizontal = 12.dp),
                                fontWeight = FontWeight.Bold
                            )
                            Divider()
                            Text(
                                "상품 조정",
                                fontSize = 15.sp,
                                modifier = modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        showFabMenu.value = false
                                        navController.navigate("StoreOwnerMenu")
                                    }
                                    .padding(vertical = 16.dp, horizontal = 12.dp),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = modifier.height(12.dp))
                }

                FloatingActionButton(
                    onClick = { showFabMenu.value = !showFabMenu.value },
                    modifier = modifier.size(60.dp),
                    containerColor = Color(0xFFE0E0E0) // closer to the image
                ) {
                    Text(
                        if (showFabMenu.value) "X" else "+",
                        fontSize = 22.sp
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = modifier.height(12.dp))
            Text(
                "예약 요청(${reservationRequests.size}개)", /*TODO : 실제 데이터 개수로 변경*/
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = modifier.height(8.dp))

            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(0.5.dp, Color(0xFF989898)),
                        shape = RoundedCornerShape(5.dp)
                    )
                    .padding(12.dp)
            ) {
                Column {
                    FlowRow(
                        modifier = modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        visibleRequests.forEach { item ->
                            Box(
                                modifier = modifier
                                    .width(100.dp)
                                    .clickable {
                                        selectedReservation.value = item.substringBefore(" -")
                                        dialogType.value = "request"
                                        showDialog.value = true
                                    }
                                    .padding(4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(item, fontWeight = FontWeight.Normal)
                                    Text("메뉴 n개", fontSize = 10.sp) // TODO: 메뉴 n개 실제 데이터 개수로 변경
                                }
                            }
                        }
                    }
                    Spacer(modifier = modifier.height(12.dp))

                    // 6개 보다 많을 때만 모두 보기 버튼 보임
                    if (reservationRequests.size > 6) {
                        Button(
                            onClick = {
                                showAllRequests.value = !showAllRequests.value
                                Toast.makeText(context, "모두보기 버튼 클릭됨!", Toast.LENGTH_SHORT).show()
                            },
                            modifier = modifier.fillMaxWidth()
                        ) {
                            Text(if (showAllRequests.value) "닫기" else "모두 보기")
                        }
                    }
                }
            }
            Spacer(modifier = modifier.height(24.dp))
            Text("예약 리스트 확인", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = modifier.height(8.dp))

            if (showDialog.value && selected != null) {
                val (name, phone) = selected.split("(").let {
                    val namePart = it[0]
                    val phonePart = it.getOrNull(1)?.removeSuffix(")") ?: "0000"
                    namePart to phonePart
                }

                when (dialogType.value) {
                    "request" -> {
                        StoreOwnerReservationDialog(
                            name = name,
                            phone = phone,
                            onDismiss = {
                                showDialog.value = false
                                dialogType.value = null
                            },
                            onAccept = {
                                Toast.makeText(context, "예약 수락됨.", Toast.LENGTH_SHORT).show()
                                showDialog.value = false
                                dialogType.value = null
                            },
                            onReject = {
                                Toast.makeText(context, "예약 거절됨.", Toast.LENGTH_SHORT).show()
                                showDialog.value = false
                                dialogType.value = null
                            }
                        )
                    }

                    "confirmed" -> {
                        val menus = reservationList.find { it.startsWith(selected) }
                            ?.substringAfter("- ")
                            ?.split(", ")
                            ?: emptyList()

                        StoreOwnerConfirmedDialog(
                            name = name,
                            phone = phone,
                            menus = menus,
                            onDismiss = {
                                showDialog.value = false
                                dialogType.value = null
                            }
                        )
                    }
                }
            }

            LazyColumn {
                items(reservationList) { item ->
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedReservation.value = item.substringBefore(" -")
                                dialogType.value = "confirmed"
                                showDialog.value = true
                            }
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(item, fontSize = 14.sp, modifier = modifier.weight(1f))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "상세 보기"
                        )
                    }
                    Divider()
                }
            }
        }
    }
    if (showStoreDialog.value) {
        StoreInfoDialog(
            onDismiss = {
                showStoreDialog.value = false
            }
        )
    }
}
