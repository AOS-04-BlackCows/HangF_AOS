package com.compose.hangf_aos.views.screens.customer.lookup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.compose.hangf_aos.data.model.Order
import com.compose.hangf_aos.views.intents.OrderIntent
import com.compose.hangf_aos.views.states.OrderState
import com.compose.hangf_aos.views.viewmodels.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LookUpUI(
    navController: NavController,
    modifier: Modifier = Modifier,
    pageName: String,
    customerName: String,
    customerPhone: String
) {
    val viewModel: OrderViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    var orderIdFilter by remember { mutableStateOf("") }
    var storeIdFilter by remember { mutableStateOf("") }
    var statusFilter by remember { mutableStateOf("") }

    LaunchedEffect(customerPhone) {
        viewModel.handleIntent(OrderIntent.GetOrdersByCustomer(customerPhone))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(pageName, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "뒤로가기")
                    }
                }
            )
        }
    ) { padding ->
        Surface(modifier = modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)) {

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = orderIdFilter,
                    onValueChange = {
                        orderIdFilter = it
                    },
                    label = { Text("주문 번호") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = storeIdFilter,
                    onValueChange = {
                        storeIdFilter = it
                    },
                    label = { Text("매장 이름 또는 ID") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = statusFilter,
                    onValueChange = {
                        statusFilter = it
                    },
                    label = { Text("주문 상태 (예: 대기, 완료 등)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(onClick = {
                    when {
                        orderIdFilter.isNotBlank() -> viewModel.handleIntent(OrderIntent.GetOrder(orderIdFilter))
                        storeIdFilter.isNotBlank() -> viewModel.handleIntent(OrderIntent.GetOrdersByStore(storeIdFilter))
                        statusFilter.isNotBlank() -> viewModel.handleIntent(OrderIntent.GetOrdersByStatus(statusFilter))
                        else -> viewModel.handleIntent(OrderIntent.GetOrdersByCustomer(customerPhone))
                    }
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("검색")
                }

                when (state) {
                    is OrderState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                    is OrderState.ListSuccess -> {
                        val orders = (state as OrderState.ListSuccess).orders
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(orders) { order ->
                                OrderItem(order)
                            }
                        }
                    }
                    is OrderState.Success -> {
                        val order = (state as OrderState.Success).order
                        order?.let {
                            OrderItem(order)
                        }
                    }
                    is OrderState.Error -> {
                        val msg = (state as OrderState.Error).message
                        Text(text = msg, color = MaterialTheme.colorScheme.error)
                    }
                    else -> {}
                }
            }
        }
    }
}

@Composable
fun OrderItem(order: Order) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = 1.dp,
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("주문 번호: ${order.id}", fontWeight = FontWeight.Bold)
            Text("고객 이름: ${order.customerName} / 전화번호: ${order.userPhoneNumber}")
            Text("매장: ${order.storeId}")
            Text("픽업 시간: ${order.pickUpTime}")
            Text("총 금액: ${order.totalPrice}원")
            Text("상태: ${order.status}")
        }
    }
}
