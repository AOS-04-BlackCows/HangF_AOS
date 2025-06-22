package com.compose.hangf_aos.views.screens.customer.confirmed

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.compose.hangf_aos.data.model.MenuOrder
import com.compose.hangf_aos.data.model.Order
import com.compose.hangf_aos.views.intents.MenuIntent
import com.compose.hangf_aos.views.intents.MenuOrderIntent
import com.compose.hangf_aos.views.intents.OrderIntent
import com.compose.hangf_aos.views.nevigation.Bookmark
import com.compose.hangf_aos.views.screens.T_AddressDialog
import com.compose.hangf_aos.views.states.MenuOrderState
import com.compose.hangf_aos.views.states.OrderState
import com.compose.hangf_aos.views.viewmodels.MenuOrderViewModel
import com.compose.hangf_aos.views.viewmodels.OrderViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.type.TimeOfDay
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmedUI(
    navController: NavController,
    modifier: Modifier = Modifier,
    pageName: String,
    customerName: String,
    customerPhone: String,
    totalPrice: String
) {
    val isExpanded = remember { mutableStateOf(true) }
    val context = LocalContext.current

    val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
    val selectedMenus = savedStateHandle?.get<List<Pair<Menu, Int>>>("menus")

    var reservedTime = remember { mutableStateOf("시간 선택") }
    var reservedDate = remember { mutableStateOf("날짜 선택") }
    val calendar = Calendar.getInstance()

    val currentYnM =
        String.format("%04d%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1)

    val reservedTimePicker = TimePickerDialog(
        LocalContext.current,
        { _, hour, minute -> reservedTime.value = String.format("%02d:%02d", hour, minute) },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )
    val reservedDatePicker = DatePickerDialog(
        LocalContext.current,
        { _, year, month, day -> reservedDate.value = String.format("%02d/%02d", month + 1, day) },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val orderViewModel: OrderViewModel = hiltViewModel()
    val menuOderViewModel: MenuOrderViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        orderViewModel.handleIntent(
            OrderIntent.GetOrdersByCustomer(customerPhone)
        )
        menuOderViewModel.handleIntent(MenuOrderIntent.GetAllMenuOrders)
    }
    val orderStatue by orderViewModel.state.collectAsState()
    val menuOrderState by menuOderViewModel.state.collectAsState()

    val orders = (orderStatue as? OrderState.ListSuccess)?.orders ?: emptyList()
    val menuOrders = (menuOrderState as? MenuOrderState.ListSuccess)?.menuOrders ?: emptyList()

    val orderLastNumber = orders.size
    val menuOrderLastNumber = menuOrders.size

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(pageName, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "뒤로가기"
                        )
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            border = BorderStroke(0.5.dp, Color(0xFF989898)),
                            shape = RoundedCornerShape(5.dp)
                        )
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { isExpanded.value = !isExpanded.value }
                        ) {
                            Text("주문자 정보", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            Icon(
                                imageVector = if (isExpanded.value) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = "Expand Customer Info"
                            )
                        }
                        if (isExpanded.value) {
                            var customerData = listOf(
                                Pair("이름", customerName),
                                Pair("전화번호", customerPhone),
                                Pair("예약 날짜", ""),
                                Pair("예약 시간", "")
                            )
                            customerData.forEach { (label, value) ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = label,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )
                                    if (label == "예약 시간") {
                                        Button(onClick = { reservedTimePicker.show() }) {
                                            Text(reservedTime.value)
                                        }
                                    } else if (label == "예약 날짜") {
                                        Button(onClick = { reservedDatePicker.show() }) {
                                            Text(reservedDate.value)
                                        }
                                    } else {
                                        Text(text = value, fontSize = 16.sp)
                                    }
                                }
                            }
                        }
                    }
                }
                Text(
                    text = "선택한 메뉴",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
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
                Button(onClick = {
                    if (reservedDate.value == "날짜 선택" || reservedTime.value == "시간 선택") {
                        Toast.makeText(context, "날짜와 시간을 선택해주세요.", Toast.LENGTH_SHORT).show()
                        return@Button
                    } else if (customerName == "" || customerPhone == "") {
                        Toast.makeText(context, "이름과 전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                        return@Button
                    } else {
                        var menuOrderList = mutableListOf<String>()
                        var menuOrderNumber = menuOrderLastNumber + 1
                        selectedMenus?.forEach { (menu, count) ->
                            menuOrderList.add(menu.id)
                            var menuOrder = MenuOrder(
                                id = currentYnM + String.format("%06d", menuOrderNumber),
                                menuId = menu.id,
                                orderId = currentYnM + String.format("%04d", orderLastNumber),
                                num = count,
                                totalPrice = menu.price * count
                            )
                            menuOderViewModel.handleIntent(MenuOrderIntent.AddMenuOrder(menuOrder))
                            menuOrderNumber++
                        }

                        var order = Order(
                            id = currentYnM + String.format("%04d", orderLastNumber),
                            storeId = "힐링쿡 용호동점",
                            customerId = customerPhone,
                            customerName = customerName,
                            userPhoneNumber = customerPhone,
                            menuOrders = menuOrderList,
                            totalPrice = totalPrice.toInt(),
                            status = com.compose.hangf_aos.data.model.OrderStatus.Pending,
                            pickUpTime = reservedDate.value + " " + reservedTime.value
                        )
                        orderViewModel.handleIntent(OrderIntent.AddOrder(order))
                        Toast.makeText(context, "주문이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                        navController.navigate(Bookmark.MainHome.name)
                    }
                }) {
                    Text(
                        text = "총 가격: ${totalPrice}원 예약하기",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
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
        Column(modifier = Modifier
            .weight(1f)
            .padding(horizontal = 8.dp)) {
            Text(text = menu.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(menu.description, fontSize = 12.sp)
            Text(
                text = "${menu.price}원 x $count",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Text(
            text = "${menu.price * count}원",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
    HorizontalDivider()
}