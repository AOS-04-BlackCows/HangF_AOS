package com.compose.hangf_aos.views.nevigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.compose.hangf_aos.data.local.LocalStorage
import com.compose.hangf_aos.views.screens.customer.confirmed.ConfirmedUI
import com.compose.hangf_aos.views.screens.customer.info.InfoUI
import com.compose.hangf_aos.views.screens.customer.reservation.ReservationUI
import com.compose.hangf_aos.views.screens.MainHome
import com.compose.hangf_aos.views.screens.owner.StoreOwnerHomeUI
import com.compose.hangf_aos.views.screens.owner.StoreOwnerMenuEditDialog
import com.compose.hangf_aos.views.screens.owner.StoreOwnerMenuScreen

@Composable
fun HangFNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()//네비게이션을 관리 하는 컨트롤러
    val context = LocalContext.current
    val localStorage = remember { LocalStorage(context) }
    val (customerName, setCustomerName) = remember { mutableStateOf("") }
    val (customerPhone, setCustomerPhone) = remember { mutableStateOf("") }

    var startDestination by remember { mutableStateOf<String?>(null) }

    // 고객 정보 확인
    LaunchedEffect(Unit) {
        val (name, phone) = localStorage.getCustomer()
        setCustomerName(name ?: "")
        setCustomerPhone(phone ?: "")
        startDestination = if (!name.isNullOrBlank() && !phone.isNullOrBlank()) {
            Bookmark.MainHome.name
        } else {
            Bookmark.CustomerInfo.name
        }
    }

    // 아직 고객 정보 로딩 중이면 아무것도 표시하지 않음 (또는 로딩 화면 표시 가능)
    if (startDestination == null) return


    //네비게이션의 컨테이너 역활을 함
    NavHost(
        navController = navController,
        startDestination = startDestination!! // 로딩 완료 후 설정
    ) {
        //Nav Graph
        // 초기 유저 정보 입력
        composable(route = Bookmark.CustomerInfo.name)
        {
            InfoUI(
                viewModel = hiltViewModel(),
                navController = navController,
                modifier = modifier,
                pageName = "유저 정보 입력"
            )
        }

        composable(route = Bookmark.MainHome.name)
        {
            MainHome(
                navController = navController,
                modifier = modifier,
                pageName = "메인 화면",
                customerName = customerName,
                customerPhone = customerPhone
            )
        }

        composable(route = Bookmark.CustomerReservation.name)
        { ReservationUI(navController = navController, modifier = modifier, pageName = "예약화면") }

        composable(route = Bookmark.CustomerConfirmed.name)
        { ConfirmedUI(navController = navController, modifier = modifier, pageName = "확정화면") }

        composable(route = Bookmark.StoreOwnerHome.name) {
            StoreOwnerHomeUI(navController = navController)
        }

        composable(route = Bookmark.StoreOwnerMenu.name) {
            StoreOwnerMenuScreen(navController = navController)
        }

        composable(
            route = "StoreOwnerMenuEditDialog/{title}/{desc}/{price}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("desc") { type = NavType.StringType },
                navArgument("price") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val desc = backStackEntry.arguments?.getString("desc") ?: ""
            val price = backStackEntry.arguments?.getString("price") ?: ""
            StoreOwnerMenuEditDialog(
                nameInit = title,
                descInit = desc,
                priceInit = price,
                onDismiss = { navController.popBackStack() }
            )
        }
    }
}