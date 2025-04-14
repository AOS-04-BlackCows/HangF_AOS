package com.compose.hangf_aos.View.Screens.StoreOwner.Home

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun StoreOwnerMenuScreen(navController: NavController, modifier: Modifier = Modifier) {

    val menuList = listOf(
        Triple("반찬 이름", "남녀노소 함께 즐기는 (반찬 설명)", "5,000원"),
        Triple("반찬 이름", "남녀노소 함께 즐기는 (반찬 설명)", "5,000원"),
        Triple("반찬 이름", "살짝 매콤", "5,000원")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("상품 조정", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                modifier = modifier.border(0.5.dp, Color(0xFF989898))
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
        ) {
            items(menuList) { (title, desc, price) ->
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .clickable {
                            navController.navigate("StoreOwnerMenuEditDialog/${title}/${desc}/${price}")
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = modifier
                            .size(64.dp)
                            .border(0.5.dp, Color.LightGray, RoundedCornerShape(4.dp))
                    ) {
                        // TODO: 이미지 삽입
                    }

                    Spacer(modifier = modifier.width(12.dp))

                    Column(modifier = modifier.weight(1f)) {
                        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text(text = desc, fontSize = 10.sp, color = Color.Gray)
                        Spacer(modifier = modifier.height(4.dp))
                        Text(text = price, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "수정 아이콘",
                        tint = Color.Black
                    )
                }
                Divider()
            }
        }
    }
}
