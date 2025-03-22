package com.compose.hangf_aos.Screens.Customer.Reservation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.compose.hangf_aos.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationUI(navController: NavController, modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
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
            "1000원"
        ),
        listOf(
            "https://cdn.pixabay.com/photo/2017/03/13/13/39/pancakes-2139844_640.jpg",
            "메뉴2",
            "2000원"
        ),
        listOf(
            "https://cdn.pixabay.com/photo/2017/01/30/13/49/pancakes-2020863_640.jpg",
            "메뉴3",
            "1000원"
        ),
        listOf(
            "https://cdn.pixabay.com/photo/2016/11/18/15/40/cookies-1835414_640.jpg",
            "메뉴4",
            "2000원"
        ),
        listOf(
            "https://cdn.pixabay.com/photo/2017/01/30/13/49/pancakes-2020863_640.jpg",
            "메뉴5",
            "1000원"
        ),
        listOf(
            "https://cdn.pixabay.com/photo/2017/01/30/13/49/pancakes-2020863_640.jpg",
            "메뉴6",
            "2000원"
        ),
        listOf(
            "https://cdn.pixabay.com/photo/2016/11/18/15/40/cookies-1835414_640.jpg",
            "메뉴7",
            "1000원"
        ),
        listOf(
            "https://cdn.pixabay.com/photo/2017/03/13/13/39/pancakes-2139844_640.jpg",
            "메뉴8",
            "2000원"
        ),
        listOf(
            "https://cdn.pixabay.com/photo/2017/01/30/13/49/pancakes-2020863_640.jpg",
            "메뉴9",
            "1000원"
        ),
        listOf(
            "https://cdn.pixabay.com/photo/2016/11/18/15/40/cookies-1835414_640.jpg",
            "메뉴10",
            "2000원"
        ),
        listOf(
            "https://cdn.pixabay.com/photo/2017/03/13/13/39/pancakes-2139844_640.jpg",
            "메뉴11",
            "1000원"
        ),
        listOf(
            "https://cdn.pixabay.com/photo/2017/01/30/13/49/pancakes-2020863_640.jpg",
            "메뉴12",
            "2000원"
        ),
    )

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {
        storeInfo(storeData = storeData)
        menuLazyColumn(modifier, menuData)
    }
}

@Composable
fun storeInfo(modifier: Modifier = Modifier, storeData: List<String>) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Red)
    ) {
        for (i in 0..storeData.size - 1 step 2) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.fillMaxWidth()
            ) {
                Text(text = "${storeData[i]}")
                Text(text = "${storeData[i + 1]}")
            }
        }
    }
}

@Composable
fun menuLazyColumn(modifier: Modifier = Modifier, menuData: List<List<String>>) {
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(menuData) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it[0])
                        .transformations(CircleCropTransformation()) //이미지 변형
                        .build(),
                    contentDescription = "메뉴 이미지",
                    error = painterResource(R.drawable.blackcow_what),//애러 떳을 때 이미지 띄워줌
                    placeholder = painterResource(R.drawable.blackcow_what),
                    modifier = modifier
                        .padding(10.dp)
                        .height(40.dp)
                )
                Column {
                    Text(text = it[1])
                    Text(text = it[2])
                }
            }
        }
    }
}


