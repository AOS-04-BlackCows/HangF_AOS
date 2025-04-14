package com.compose.hangf_aos.View.Screens.StoreOwner.Home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StoreOwnerConfirmedDialog(
    modifier: Modifier = Modifier,
    name: String,
    phone: String,
    menus: List<String>,
    onDismiss: () -> Unit
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
                Text(phone, fontWeight = FontWeight.Bold)
                Spacer(modifier = modifier.height(4.dp))
                Text("2025-04-06    14:30")

                Spacer(modifier = modifier.height(12.dp))

                Box(
                    modifier = modifier
                        .heightIn(max = 150.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        menus.forEach {
                            Text(it)
                        }
                    }
                }

                Spacer(modifier = modifier.height(12.dp))
            }
        }
    )
}
