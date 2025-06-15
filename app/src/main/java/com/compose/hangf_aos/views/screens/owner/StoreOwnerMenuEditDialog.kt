package com.compose.hangf_aos.views.screens.owner

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.compose.hangf_aos.R
import com.compose.hangf_aos.data.model.Menu
import com.compose.hangf_aos.views.intents.MenuIntent
import com.compose.hangf_aos.views.viewmodels.MenuViewModel

@Composable
fun StoreOwnerMenuEditDialog(
    onDismiss: () -> Unit,
    nameInit: String = "반찬 이름",
    descInit: String = "",
    priceInit: String = "5000"
) {
    val viewModel: MenuViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    var name by remember { mutableStateOf(nameInit) }
    var description by remember { mutableStateOf(descInit) }
    var price by remember { mutableStateOf(priceInit) }

    val menuRandomID = (0..200).random().toString()

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    var menu = Menu(
                        id = menuRandomID,
                        storeId = "힐링쿡 용호동점",
                        name = name,
                        pictureUrl = "https://cdn.pixabay.com/photo/2016/11/18/15/40/cookies-1835414_640.jpg",
                        description = description,
                        price = price.replace(",","").replace("원","").trim().toInt(),
                        isActive = true
                    )
                    viewModel.handleIntent(MenuIntent.AddMenu(menu))
                    Toast.makeText(context, "저장 완료", Toast.LENGTH_SHORT).show()
                    onDismiss()
                }) {
                Text("저장")
            }
        },
        title = null,
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .border(1.dp, Color.LightGray, CircleShape)
                        .clickable { /* TODO: 이미지 변경 로직 */ },
                    contentAlignment = Alignment.Center
                ) {
                    /*TODO: 이미지 로딩 및 변경*/
                    Image(
                        painter = painterResource(id = R.drawable.blackcow_what),
                        contentDescription = "반찬 이미지"
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("반찬 이름") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { if (it.length <= 20) description = it },
                    label = { Text("반찬 상세 설명 (20자 이내)") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("가격") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }
    )
}
