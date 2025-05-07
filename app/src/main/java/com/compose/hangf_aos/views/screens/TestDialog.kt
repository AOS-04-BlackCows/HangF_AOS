package com.compose.hangf_aos.views.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.compose.hangf_aos.R
import com.compose.hangf_aos.data.model.DayOnTime
import com.compose.hangf_aos.data.model.Menu
import com.compose.hangf_aos.data.model.Store
import com.compose.hangf_aos.views.intents.MenuIntent
import com.compose.hangf_aos.views.intents.StoreIntent
import com.compose.hangf_aos.views.states.StoreState
import com.compose.hangf_aos.views.viewmodels.AddressViewModel
import com.compose.hangf_aos.views.viewmodels.MenuViewModel
import com.compose.hangf_aos.views.viewmodels.StoreViewModel
import java.util.Calendar

@Composable
fun T_StoreInfoDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    val viewModel: StoreViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

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

                when (state) {
                    is StoreState.Loading -> CircularProgressIndicator()
                    is StoreState.Success -> {
                        val store = (state as StoreState.Success).store
                        store?.let {
                            Toast.makeText(context, "저장 완료", Toast.LENGTH_SHORT).show()
                        } ?: Toast.makeText(context, "저장 실패", Toast.LENGTH_SHORT).show()
                    }

                    is StoreState.Error -> Toast.makeText(
                        context,
                        (state as StoreState.Error).message,
                        Toast.LENGTH_SHORT
                    ).show()

                    else -> {}
                }

                TextButton(onClick = {
                    var dayOnTimeList = mutableListOf(
                        DayOnTime(
                            "${calendar.get(Calendar.DAY_OF_WEEK)}",
                            openTime,
                            closeTime
                        )
                    )
                    val store = Store(
                        id = storeName,
                        name = storeName,
                        address = etc,
                        phoneNumber = "01077628540",
                        dayOnTime = dayOnTimeList
                    )
//                    val prefs = context.getSharedPreferences("store_info", Context.MODE_PRIVATE)
//                    prefs.edit {
//                        putString("store_name", storeName)
//                        putString("open_time", openTime)
//                        putString("close_time", closeTime)
//                        putString("start_date", startDate)
//                        putString("end_date", endDate)
//                        putString("etc", etc)
//                    }

                    viewModel.handleIntent(StoreIntent.AddStore(store))
                    Toast.makeText(context, "저장 완료", Toast.LENGTH_SHORT).show()
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

                HorizontalDivider()

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

                HorizontalDivider()

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

                HorizontalDivider()

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

                HorizontalDivider()

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

                HorizontalDivider()
            }
        }
    )
}

@Composable
fun T_MenuEditDialog(
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
                // 더미 이미지들
                // "https://cdn.pixabay.com/photo/2016/11/18/15/40/cookies-1835414_640.jpg"
                // "https://cdn.pixabay.com/photo/2017/03/13/13/39/pancakes-2139844_640.jpg"
                // "https://cdn.pixabay.com/photo/2017/01/30/13/49/pancakes-2020863_640.jpg"
                onClick = {
                    var menu = Menu(
                        id = menuRandomID,
                        storeId = "힐링쿡 용호동점",
                        name = name,
                        pictureUrl = "https://cdn.pixabay.com/photo/2016/11/18/15/40/cookies-1835414_640.jpg",
                        description = description,
                        price = price.toInt(),
                        isActive = true
                    )
                    Toast.makeText(context, "저장 완료", Toast.LENGTH_SHORT).show()
                    viewModel.handleIntent(MenuIntent.AddMenu(menu))
                    onDismiss()
                }
            ) {
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

@Composable
fun T_AddressDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    val viewModel: AddressViewModel = hiltViewModel()
    var address by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {

            TextButton(onClick = { onDismiss() }) {
                Text("닫기")
            }
        },
        title = null,
        text = {
            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Box (
                    modifier = modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.LightGray, CircleShape)
                        .padding(12.dp)
                ){

                    TextField(value = address, onValueChange = { address = it })

                }
                TextButton(onClick = { viewModel.getRegionSearch(address) }) {
                    Text("검색")
                }
            }
        }
    )
}