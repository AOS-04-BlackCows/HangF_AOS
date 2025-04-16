package com.compose.hangf_aos.views.nevigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.compose.hangf_aos.views.screens.customer.confirmed.ConfirmedUI
import com.compose.hangf_aos.views.screens.customer.info.InfoUI
import com.compose.hangf_aos.views.screens.customer.reservation.ReservationUI
import com.compose.hangf_aos.views.screens.MainHome
import com.compose.hangf_aos.views.screens.owner.StoreOwnerHomeUI
import com.compose.hangf_aos.views.screens.owner.StoreOwnerMenuEditDialog
import com.compose.hangf_aos.views.screens.owner.StoreOwnerMenuScreen

@Composable
fun HangFNavigation(pageName: String, modifier: Modifier = Modifier) {
    val navController = rememberNavController()//네비게이션을 관리 하는 컨트롤러

    //네비게이션의 컨테이너 역활을 함
    NavHost(
        navController = navController,
        startDestination = Bookmark.CustomerInfo.name
    ) {
        //Nav Graph
        // 초기 유저 정보 입력
        composable(route = Bookmark.CustomerInfo.name)
        {
            InfoUI(viewModel = hiltViewModel(), navController = navController, modifier = modifier, pageName) }

        composable(
            route = Bookmark.MainHome.name + "/{name},{phone}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("phone") { type = NavType.StringType },
            )
        )
        {
            MainHome(
                navController = navController,
                modifier = modifier,
                pageName = "메인 화면",
                it.arguments?.getString("name"),
                it.arguments?.getString("phone")
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