package com.compose.hangf_aos.Screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.compose.hangf_aos.R
import com.compose.hangf_aos.nevigation.Bookmark

@Composable
fun MainHome(navController: NavController, modifier: Modifier = Modifier, name: String?, phone: String?) {
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        AsyncImage(
//            model = "https://cdn.pixabay.com/photo/2024/09/08/20/30/architecture-9033164_1280.jpg", //coil 방식
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://cdn.pixabay.com/photo/2024/09/08/20/30/architecture-9033164_1280.jpg")
                .crossfade(2000)//정해진 시간 동안 이미지를 천천히 띄워줌
                .transformations(CircleCropTransformation()) //이미지 변형
                .build(),
            contentDescription = "이미지 임",
            error = painterResource(R.drawable.blackcow_what),//애러 떳을 때 이미지 띄워줌
            placeholder = painterResource(R.drawable.blackcow_what),//이미지 로드 전에 띄워줄 이미지
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = modifier.height(20.dp))

        Text(text = "홈 화면")
        Text(text = "이름 : $name")
        Text(text = "전화번호 : $phone")
        Spacer(modifier = modifier.height(20.dp))

        Button(onClick = { navController.navigate(Bookmark.CustomerReservation.name) }) {
            Text(text = "예약 화면")
        }
        Button(onClick = { navController.navigate(Bookmark.CustomerConfirmed.name) }) {
            Text(text = "예약 확정 화면")
        }
        Button(onClick = { Toast.makeText(context, "조회 화면", Toast.LENGTH_SHORT).show() }) {
            Text(text = "조회 화면")
        }
    }
}