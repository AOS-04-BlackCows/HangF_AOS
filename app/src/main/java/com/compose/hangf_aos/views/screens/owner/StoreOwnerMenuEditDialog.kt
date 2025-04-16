package com.compose.hangf_aos.views.screens.owner

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.compose.hangf_aos.R

@Composable
fun StoreOwnerMenuEditDialog(
    onDismiss: () -> Unit,
    nameInit: String = "반찬 이름",
    descInit: String = "",
    priceInit: String = "5000"
) {
    var name by remember { mutableStateOf(nameInit) }
    var description by remember { mutableStateOf(descInit) }
    var price by remember { mutableStateOf(priceInit) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
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
                    // TODO: 이미지 로딩 및 변경
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
