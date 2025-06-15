package com.compose.hangf_aos.views.screens.owner

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 예약 요청 다이얼로그
@Composable
fun StoreOwnerReservationDialog(
    modifier: Modifier = Modifier,
    name : String,
    phone : String,
    onDismiss: () -> Unit,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        title = null,
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = modifier.height(8.dp))
                Text(name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(phone, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = modifier.height(4.dp))
                Text("2025-04-06    14:30")

                Spacer(modifier = modifier.height(12.dp))
                Box(
                    modifier = modifier
                        .heightIn(max = 150.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text("메뉴보기 1")
                        Text("메뉴보기 2")
                        Text("메뉴보기 3")
                        Text("메뉴보기 4")
                        Text("메뉴보기 5")
                        Text("메뉴보기 6")
                        Text("메뉴보기 6")
                        Text("메뉴보기 6")
                        Text("메뉴보기 6")
                        Text("메뉴보기 6")
                        Text("메뉴보기 6")
                        Text("메뉴보기 6")
                        Text("메뉴보기 6")
                    }
                }

                Spacer(modifier = modifier.height(20.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = modifier.align(Alignment.CenterHorizontally)
                ) {
                    Button(onClick = onAccept) {
                        Text("수락")
                    }
                    OutlinedButton(onClick = onReject) {
                        Text("거절")
                    }
                }

                Spacer(modifier = modifier.height(8.dp))
            }
        }
    )
}