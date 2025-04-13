package com.compose.hangf_aos.views.screens.customer.reservation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.compose.hangf_aos.R
import com.compose.hangf_aos.views.nevigation.Bookmark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationUI(navController: NavController, modifier: Modifier = Modifier, pageName: String) {
    val storeData = listOf(
        "매장 이름: ", "힐링쿡 - 용호동점",
        "영업 시간: ", "09:00 - 20:00",
        "영업일: ", "월, 화, 수, 목, 금, 토",
        "매장 전화번호: ", "051) 621-3700",
        "매장 주소: ", "부산 남구 용호동 197"
    )
    val menuData = listOf(
        listOf(
            "https://cdn.pixabay.com/photo/2016/11/18/15/40/cookies-1835414_640.jpg",
            "메뉴1",
            "반찬 설명 반찬 설명 반찬 설명 반찬 설명 ",
            "1000원","0"
        ),
        listOf(
            "https://cdn.pixabay.com/photo/2017/03/13/13/39/pancakes-2139844_640.jpg",
            "메뉴2",
            "반찬 설명 반찬 설명 반찬 설명 반찬 설명 ",
            "2000원","0"
        ),
        listOf(
            "https://cdn.pixabay.com/photo/2017/01/30/13/49/pancakes-2020863_640.jpg",
            "메뉴3",
            "반찬 설명 반찬 설명 반찬 설명 반찬 설명 ",
            "1000원","0"
        ),
        listOf(
            "https://cdn.pixabay.com/photo/2016/11/18/15/40/cookies-1835414_640.jpg",
            "메뉴4",
            "반찬 설명 반찬 설명 반찬 설명 반찬 설명 ",
            "2000원","0"
        ),
        listOf(
            "https://cdn.pixabay.com/photo/2017/01/30/13/49/pancakes-2020863_640.jpg",
            "메뉴5",
            "반찬 설명 반찬 설명 반찬 설명 반찬 설명 ",
            "1000원","0"
        ),
        listOf(
            "https://cdn.pixabay.com/photo/2017/01/30/13/49/pancakes-2020863_640.jpg",
            "메뉴6",
            "반찬 설명 반찬 설명 반찬 설명 반찬 설명 ",
            "2000원","0"
        ),
        listOf(
            "https://cdn.pixabay.com/photo/2016/11/18/15/40/cookies-1835414_640.jpg",
            "메뉴7",
            "반찬 설명 반찬 설명 반찬 설명 반찬 설명 ",
            "1000원","0"
        ),
        listOf(
            "https://cdn.pixabay.com/photo/2017/03/13/13/39/pancakes-2139844_640.jpg",
            "메뉴8",
            "반찬 설명 반찬 설명 반찬 설명 반찬 설명 ",
            "2000원","0"
        ),
        listOf(
            "https://cdn.pixabay.com/photo/2017/01/30/13/49/pancakes-2020863_640.jpg",
            "메뉴9",
            "반찬 설명 반찬 설명 반찬 설명 반찬 설명 ",
            "1000원","0"
        ),
        listOf(
            "https://cdn.pixabay.com/photo/2016/11/18/15/40/cookies-1835414_640.jpg",
            "메뉴10",
            "반찬 설명 반찬 설명 반찬 설명 반찬 설명 ",
            "2000원","0"
        ),
        listOf(
            "https://cdn.pixabay.com/photo/2017/03/13/13/39/pancakes-2139844_640.jpg",
            "메뉴11",
            "반찬 설명 반찬 설명 반찬 설명 반찬 설명 ",
            "1000원","0"
        ),
        listOf(
            "https://cdn.pixabay.com/photo/2017/01/30/13/49/pancakes-2020863_640.jpg",
            "메뉴12",
            "반찬 설명 반찬 설명 반찬 설명 반찬 설명 ",
            "2000원","0"
        ),
    )

    val context = LocalContext.current
    val (clicks, setClicks) = remember { mutableStateOf(0) }
    val scrollState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "${pageName}") },
                navigationIcon = { // 뒤로가기 버튼 - 유저 정보 변경 활성화시 주석 해제
                    IconButton(onClick = {
//                        navController.navigate(Bookmark.MainHome.name)
                        Toast.makeText(context, "뒤로가기", Toast.LENGTH_SHORT).show()
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
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "장바구니",
                            modifier = modifier
                                .size(32.dp)
                                .padding(top = 4.dp, end = 4.dp),
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            Button(
                onClick = { navController.navigate(Bookmark.CustomerConfirmed.name) },
                modifier = modifier.fillMaxWidth(),
                content = { Text(text = "예약하기") }
            )
        },
        floatingActionButtonPosition = androidx.compose.material3.FabPosition.Center,
        content = {
            Surface(
                modifier = modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Column {
                    Column(//TODO:스피너로 접을수 있게 만들면 어떨까...
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier.padding(horizontal = 16.dp),
                        content = {
                            for (i in 0..storeData.size - 1 step 2) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .padding(4.dp)
                                ) {
                                    Text(text = "${storeData[i]}")
                                    Text(text = "${storeData[i + 1]}")
                                }
                            }
                        }
                    )
                    //메뉴 리스트 영역
                    Column {
                        //TODO:
                        // 박스로 싸서 영역을 만들면 패딩 안줘도 크기 잡힐듯 함
                        // 개별 수량 올라갈 수 있게 변경 해야됨
                        LazyColumn(
                            state = scrollState,
                            modifier = modifier
                                .fillMaxHeight()
                                .padding(bottom = 80.dp)
                        ) {
                            items(menuData) {
                                var aa = it[4]
                                Log.d("test", "is -- ${aa}")
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .padding(4.dp)
                                ) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(it[0])
                                            .transformations(CircleCropTransformation())
                                            .build(),
                                        contentDescription = "메뉴 이미지",
                                        error = painterResource(R.drawable.blackcow_what),//애러 떳을 때 이미지 띄워줌
                                        placeholder = painterResource(R.drawable.blackcow_what),
                                        modifier = modifier
                                            .size(60.dp)
                                            .padding(4.dp)
                                    )
                                    Column(
                                        verticalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.size(155.dp, 60.dp)
                                    ) {
                                        Text(
                                            text = it[1],
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(text = it[2], fontSize = 10.sp)
                                        Text(
                                            text = it[3],
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    TextButton(
                                        onClick = { setClicks(clicks + 1); aa = (it[4].toInt() + 1).toString() },
                                        content = {
                                            AsyncImage(
                                                model = ImageRequest.Builder(LocalContext.current)
                                                    .data(R.drawable.baseline_add_24).build(),
                                                contentDescription = "추가",
                                                modifier = modifier.size(20.dp).fillMaxSize()
                                            )
                                        }
                                    )
                                    Text(text = aa)
                                    TextButton(
                                        onClick = { if (clicks > 0) setClicks(clicks - 1); aa = (it[4].toInt() - 1).toString() },
                                        content = {
                                            AsyncImage(
                                                model = ImageRequest.Builder(LocalContext.current)
                                                    .data(R.drawable.baseline_remove_24).build(),
                                                contentDescription = "삭제",
                                                modifier = modifier.size(20.dp).fillMaxSize()
                                            )
                                        }
                                    )
                                }
                            }
                        }

                    }
                }
            }
        }
    )
}

//@Composable
//fun storeInfo(modifier: Modifier = Modifier, storeData: List<String>) {
//    Column(
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,
//        content = {
//            for (i in 0..storeData.size - 1 step 2) {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    modifier = modifier
//                        .fillMaxWidth()
//                        .background(Color.Red)
//                        .padding(10.dp)
//                ) {
//                    Text(text = "${storeData[i]}")
//                    Text(text = "${storeData[i + 1]}")
//                }
//            }
//        }
//    )
//}
//
//@Composable
//fun menuLazyColumn(
//    navController: NavController,
//    modifier: Modifier = Modifier,
//    menuData: List<List<String>>
//) {
//    val scrollState = rememberLazyListState()
//
//    Surface(
//
//    ) {
//        Column {
//            LazyColumn(
//                state = scrollState
//            ) {
//                items(menuData) {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        modifier = modifier
//                            .fillMaxWidth()
//                            .padding(10.dp)
//                    ) {
//                        AsyncImage(
//                            model = ImageRequest.Builder(LocalContext.current)
//                                .data(it[0])
//                                .transformations(CircleCropTransformation())
//                                .build(),
//                            contentDescription = "메뉴 이미지",
//                            error = painterResource(R.drawable.blackcow_what),//애러 떳을 때 이미지 띄워줌
//                            placeholder = painterResource(R.drawable.blackcow_what),
//                            modifier = modifier
//                                .size(100.dp)
//                                .padding(4.dp)
//                        )
//                        Column(
//                            verticalArrangement = Arrangement.SpaceBetween,
//                            modifier = Modifier.size(150.dp, 120.dp)
//                        ) {
//                            Text(text = it[1], fontSize = 30.sp, fontWeight = FontWeight.Bold)
//                            Text(text = "반찬 설명", fontSize = 20.sp)
//                            Text(text = it[2], fontSize = 30.sp, fontWeight = FontWeight.Bold)
//                        }
//                        Button(onClick = { /*TODO*/ }) {
//                            Text(text = "+", fontSize = 20.sp)
//                        }
//                        Text(text = "0")
//                        Button(onClick = { /*TODO*/ }) {
//                            Text(text = "-", fontSize = 20.sp)
//                        }
//                    }
//                }
//            }
//            Button(onClick = { navController.navigate(Bookmark.CustomerConfirmed.name) }) {
//                Text(text = "예약하기")
//            }
//        }
//    }
//}


