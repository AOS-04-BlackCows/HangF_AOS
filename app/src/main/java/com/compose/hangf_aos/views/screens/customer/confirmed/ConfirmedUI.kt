package com.compose.hangf_aos.views.screens.customer.confirmed

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.compose.hangf_aos.data.model.Menu
import com.compose.hangf_aos.views.nevigation.Bookmark
import com.compose.hangf_aos.views.viewmodels.SharedOrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  ConfirmedUI(navController: NavController, modifier: Modifier = Modifier, pageName: String) {

    val sharedOrderViewModel: SharedOrderViewModel = hiltViewModel() //인스턴스를 새로 만들어 버려서 안됨...

    val selectedMenus by sharedOrderViewModel.selectedMenus.collectAsState()
    val totalPrice by sharedOrderViewModel.totalPrice.collectAsState()
    Log.d("Menus_Confirmed selectedMenus", selectedMenus.toString())
    Log.d("Menus_Confirmed totalPrice", totalPrice.toString())

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
    ) { paddingValues ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "선택한 메뉴",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(selectedMenus.toList()) { (menu, count) ->
                        MenuItemSummary(menu = menu, count = count)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "총 가격: ${totalPrice}원",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.End)
                )
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
        Column(modifier = Modifier.weight(1f)) {
            Text(text = menu.name, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Text(text = "${menu.price}원 x $count", fontSize = 14.sp)
        }
        Text(
            text = "${menu.price * count}원",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}