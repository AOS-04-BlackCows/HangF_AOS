package com.compose.hangf_aos.views.screens.customer.confirmed

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.compose.hangf_aos.R
import com.compose.hangf_aos.data.model.Menu
import com.compose.hangf_aos.views.nevigation.Bookmark
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  ConfirmedUI(navController: NavController, modifier: Modifier = Modifier, pageName: String, /*menus: String,*/ totalPrice: String) {

    val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
    val selectedMenus = savedStateHandle?.get<List<Pair<Menu, Int>>>("menus")
//    val selectedMenus: Map<Menu, Int> = remember(menus) {
//        try {
//            val decodedJson = java.net.URLDecoder.decode(menus, java.nio.charset.StandardCharsets.UTF_8.toString())
//            val type = object : TypeToken<List<Pair<Menu, Int>>>() {}.type
//            val parsedList: List<Pair<Menu, Int>> = Gson().fromJson(decodedJson, type)
//            parsedList.toMap()
//        } catch (e: Exception) {
//            Log.e("ConfirmedUI", "역직렬화 실패", e)
//            emptyMap()
//        }
//    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(pageName, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "뒤로가기")
                    }
                }
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "선택한 메뉴",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    if (selectedMenus != null) {
                        items(selectedMenus.toList()) { (menu, count) ->
                            MenuItemSummary(menu = menu, count = count)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "총 가격: ${totalPrice}원",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

@Composable
fun MenuItemSummary(menu: Menu, count: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
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
        Column(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
            Text(text = menu.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(menu.description, fontSize = 12.sp)
            Text(text = "${menu.price}원 x $count", fontSize = 14.sp,fontWeight = FontWeight.SemiBold)
        }
        Text(
            text = "${menu.price * count}원",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
    HorizontalDivider()
}