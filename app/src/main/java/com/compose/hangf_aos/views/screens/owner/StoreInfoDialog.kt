package com.compose.hangf_aos.views.screens.owner

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.app.TimePickerDialog
import android.app.DatePickerDialog
import androidx.compose.ui.platform.LocalContext
import java.util.*
import android.content.Context
import androidx.core.content.edit

@Composable
fun StoreInfoDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    // rememberSaveable을 사용해 다이얼로그 닫았다 다시 열어도 값 유지
    var storeName by rememberSaveable { mutableStateOf("가게 이름") }
    var openTime by rememberSaveable { mutableStateOf("00:00") }
    var closeTime by rememberSaveable { mutableStateOf("00:00") }
    var startDate by rememberSaveable { mutableStateOf("MM/DD") }
    var endDate by rememberSaveable { mutableStateOf("MM/DD") }
    var etc by rememberSaveable { mutableStateOf("?") }
    var openDays by rememberSaveable { mutableStateOf("$startDate - $endDate") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    LaunchedEffect(Unit) {
        val prefs = context.getSharedPreferences("store_info", Context.MODE_PRIVATE)
        storeName = prefs.getString("store_name", storeName) ?: storeName
        openTime = prefs.getString("open_time", openTime) ?: openTime
        closeTime = prefs.getString("close_time", closeTime) ?: closeTime
        startDate = prefs.getString("start_date", startDate) ?: startDate
        endDate = prefs.getString("end_date", endDate) ?: endDate
        etc = prefs.getString("etc", etc) ?: etc
        openDays = "$startDate - $endDate"
    }


    val openTimePicker = TimePickerDialog(
        context,
        { _, hour, minute -> openTime = String.format("%02d:%02d", hour, minute) },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    val closeTimePicker = TimePickerDialog(
        context,
        { _, hour, minute -> closeTime = String.format("%02d:%02d", hour, minute) },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    val startDatePicker = DatePickerDialog(
        context,
        { _, year, month, day ->
            startDate = String.format("%02d/%02d", month + 1, day)
            openDays = "$startDate - $endDate"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val endDatePicker = DatePickerDialog(
        context,
        { _, year, month, day ->
            endDate = String.format("%02d/%02d", month + 1, day)
            openDays = "$startDate - $endDate"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Row {
                TextButton(onClick = {
                    storeName = "가게 이름"
                    openTime = "00:00"
                    closeTime = "00:00"
                    startDate = "MM/DD"
                    endDate = "MM/DD"
                    openDays = "$startDate - $endDate"
                    etc = "?"
                }) {
                    Text("초기화")
                }

                Spacer(modifier = modifier.width(8.dp))

                TextButton(onClick = {
                    val prefs = context.getSharedPreferences("store_info", Context.MODE_PRIVATE)
                    prefs.edit {
                        putString("store_name", storeName)
                        putString("open_time", openTime)
                        putString("close_time", closeTime)
                        putString("start_date", startDate)
                        putString("end_date", endDate)
                        putString("etc", etc)
                    }
                    onDismiss()
                }) {
                    Text("저장")
                }
            }
        },
        title = null,
        text = {
            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("매장 이름", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    TextField(
                        value = storeName,
                        onValueChange = { storeName = it },
                        modifier = modifier.width(150.dp),
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End)
                    )
                }

                Divider()

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("오픈 시간", fontSize = 14.sp)
                    Button(onClick = { openTimePicker.show() }) {
                        Text(openTime)
                    }
                }

                Divider()

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("마감 시간", fontSize = 14.sp)
                    Button(onClick = { closeTimePicker.show() }) {
                        Text(closeTime)
                    }
                }

                Divider()

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("영업 일정", fontSize = 14.sp)
                    Row {
                        Button(onClick = { startDatePicker.show() }) {
                            Text(startDate)
                        }
                        Spacer(modifier = modifier.width(4.dp))
                        Text("~", modifier = modifier.align(Alignment.CenterVertically))
                        Spacer(modifier = modifier.width(4.dp))
                        Button(onClick = { endDatePicker.show() }) {
                            Text(endDate)
                        }
                    }
                }

                Divider()

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("주소?", fontSize = 14.sp)
                    TextField(
                        value = etc,
                        onValueChange = { etc = it },
                        modifier = modifier.width(150.dp),
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End)
                    )
                }

                Divider()
            }
        }
    )
}