package com.compose.hangf_aos.views.screens.customer.info

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.compose.hangf_aos.views.intents.CustomerIntent
import com.compose.hangf_aos.views.states.CustomerState
import com.compose.hangf_aos.views.nevigation.Bookmark
import com.compose.hangf_aos.data.model.Customer
import com.compose.hangf_aos.views.viewmodels.CustomerViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoUI(
    viewModel: CustomerViewModel = hiltViewModel(),
    navController: NavController,
    modifier: Modifier = Modifier,
    pageName: String
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    val state by viewModel.state.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var existingName by remember { mutableStateOf("") }
    var isPending by remember { mutableStateOf(false) }

    val nameError = name.isNotEmpty() && !name.matches(Regex("^[가-힣a-zA-Z]{2,10}$"))
    val phoneError = phone.isNotEmpty() && !phone.matches(Regex("^(010|051|055|070)[0-9]{7,8}$"))
    val isValid = !nameError && !phoneError && name.isNotEmpty() && phone.isNotEmpty()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(pageName) },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Bookmark.StoreOwnerHome.name)
                    }) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = "관리자 로그인", tint = Color.Black)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("기본 정보를 입력후 [홈 으로] 버튼을 눌러 주세요.")
            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                isError = nameError,
                label = { Text("이름") },
                supportingText = {
                    if (nameError) Text("이름은 한글/영문 2~10자")
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                textStyle = TextStyle(fontSize = 24.sp)
            )

            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                isError = phoneError,
                label = { Text("전화번호") },
                supportingText = {
                    if (phoneError) Text("010, 051, 055, 070으로 시작하는 10~11자리 번호")
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                textStyle = TextStyle(fontSize = 24.sp)
            )

            Spacer(Modifier.height(10.dp))

            if (isValid) {
                Button(onClick = {
                    isPending = true
                    viewModel.handleIntent(CustomerIntent.GetCustomer(phone))

                }) {
                    Text("홈으로")
                }
            }

            when (state) {
                is CustomerState.Loading -> CircularProgressIndicator()
                is CustomerState.Success -> {
                    if (isPending) {
                        isPending = false
                        val customer = (state as CustomerState.Success).customer
                        if (customer == null) {
                            viewModel.handleIntent(CustomerIntent.AddCustomer(Customer(name, phone)))
                            navController.navigate("${Bookmark.MainHome.name}?customerName=$name&customerPhone=$phone")
                        } else if (customer.name != name) {
                            existingName = customer.name
                            showDialog = true
                        } else {
                            navController.navigate("${Bookmark.MainHome.name}?customerName=$name&customerPhone=$phone")
                        }
                    }
                }
                is CustomerState.Error -> {
                    Toast.makeText(context, (state as CustomerState.Error).message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("기존 이름을 새로운 이름으로 변경하시겠습니까?") },
            text = {
                Column {
                    Text("기존 이름: $existingName")
                    Text("새 이름: $name")
                    Text("전화번호: $phone")
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.handleIntent(CustomerIntent.UpdateCustomer(Customer(name, phone)))
                    showDialog = false
                    navController.navigate("${Bookmark.MainHome.name}?customerName=$name&customerPhone=$phone")
                }) {
                    Text("변경")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    navController.navigate("${Bookmark.MainHome.name}?customerName=$existingName&customerPhone=$phone")
                }) {
                    Text("유지")
                }
            }
        )
    }
}
