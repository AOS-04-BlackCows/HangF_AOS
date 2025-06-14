package com.compose.hangf_aos.views.screens.owner

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.hilt.navigation.compose.hiltViewModel
import com.compose.hangf_aos.data.model.DayOnTime
import com.compose.hangf_aos.data.model.Store
import com.compose.hangf_aos.views.intents.StoreIntent
import com.compose.hangf_aos.views.states.StoreState
import com.compose.hangf_aos.views.viewmodels.StoreViewModel
import java.util.*

// 매장 정보 수정
@Composable
fun StoreInfoDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    val viewModel: StoreViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    // rememberSaveable을 사용해 다이얼로그 닫았다 다시 열어도 값 유지
    var storeName by rememberSaveable { mutableStateOf("") }
    var openTime by rememberSaveable { mutableStateOf("00:00") }
    var closeTime by rememberSaveable { mutableStateOf("00:00") }
    var startDate by rememberSaveable { mutableStateOf("MM/DD") }
    var endDate by rememberSaveable { mutableStateOf("MM/DD") }
    var etc by rememberSaveable { mutableStateOf("") }
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
                    storeName = ""
                    openTime = "00:00"
                    closeTime = "00:00"
                    startDate = "MM/DD"
                    endDate = "MM/DD"
                    openDays = "$startDate - $endDate"
                    etc = ""
                }) {
                    Text("초기화")
                }

                Spacer(modifier = modifier.width(8.dp))

//                when (state) {
//                    is StoreState.Loading -> CircularProgressIndicator()
//                    is StoreState.Success -> {
//                        val store = (state as StoreState.Success).store
//                        store?.let {
//                            Toast.makeText(context, "저장 완료", Toast.LENGTH_SHORT).show()
//                        } ?: Toast.makeText(context, "저장 실패", Toast.LENGTH_SHORT).show()
//                    }

//                    is StoreState.Error -> Toast.makeText(
//                        context,
//                        (state as StoreState.Error).message,
//                        Toast.LENGTH_SHORT
//                    ).show()
//
//                    else -> {}
//                }

                TextButton(onClick = {
                    var dayOnTimeList = mutableListOf(
                        DayOnTime(
                            "\${calendar.get(Calendar.DAY_OF_WEEK)}",
                            openTime,
                            closeTime
                        )
                    )
                    val store =Store(
                        id = storeName,
                        name = storeName,
                        address = etc,
                        phoneNumber = "01077628540",
                        dayOnTime = dayOnTimeList
                    )

                    viewModel.handleIntent(StoreIntent.AddStore(store))

                    val prefs = context.getSharedPreferences("store_info", Context.MODE_PRIVATE)
                    prefs.edit().apply() {
                        putString("store_name", storeName)
                        putString("open_time", openTime)
                        putString("close_time", closeTime)
                        putString("start_date", startDate)
                        putString("end_date", endDate)
                        putString("etc", etc)
                        apply()
                    }
                    onDismiss ()
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
                    OutlinedTextField(
                        value = storeName,
                        onValueChange = { storeName = it },
                        label = { Text("매장 이름") },
                        modifier = modifier.fillMaxWidth(),
                        singleLine = true,
                    )
                }

                HorizontalDivider()

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("오픈 시간", fontSize = 14.sp)
                    Button (
                        onClick = { openTimePicker.show() },
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3C4A83))
                    ){
                        Text(openTime, color = Color.White)
                    }
                }

                HorizontalDivider()

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("마감 시간", fontSize = 14.sp)
                    Button (
                            onClick = { closeTimePicker.show() },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3C4A83))
                    ){
                    Text(closeTime, color = Color.White)
                }
                }

                HorizontalDivider()

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("영업 일정", fontSize = 14.sp)
                    Row {
                        Button (
                            onClick = { startDatePicker.show() },
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3C4A83))
                        ){
                            Text(startDate, color = Color.White)
                        }
                        Spacer(modifier = modifier.width(4.dp))
                        Text("~", modifier = modifier.align(Alignment.CenterVertically))
                        Spacer(modifier = modifier.width(4.dp))
                        Button (
                            onClick = { endDatePicker.show() },
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3C4A83))
                        ){
                            Text(endDate, color = Color.White)
                        }
                    }
                }

                HorizontalDivider()

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = etc,
                        onValueChange = { etc = it },
                        label = { Text("주소") },
                        modifier = modifier.fillMaxWidth(),
                        singleLine = true,
                    )
                }

                HorizontalDivider()
            }
        }
    )
}