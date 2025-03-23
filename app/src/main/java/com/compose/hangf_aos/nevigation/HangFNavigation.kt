package com.compose.hangf_aos.nevigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.compose.hangf_aos.Screens.Customer.Confirmed.ConfirmedUI
import com.compose.hangf_aos.Screens.Customer.Info.InfoUI
import com.compose.hangf_aos.Screens.Customer.Reservation.ReservationUI
import com.compose.hangf_aos.Screens.home.MainHome

@Composable
fun HangFNavigation(pageName: String, modifier: Modifier = Modifier){
    val navController = rememberNavController()//네비게이션을 관리 하는 컨트롤러

    //네비게이션의 컨테이너 역활을 함
    NavHost(
        navController = navController,
        startDestination = Bookmark.CustomerInfo.name
    ) {
        //Nav Graph
        composable(
            route = Bookmark.MainHome.name+"/{name},{phone}",
            arguments = listOf(
                navArgument("name"){type = NavType.StringType},
                navArgument("phone"){type = NavType.StringType},
            ))
        {
            MainHome(
                navController = navController,
                modifier = Modifier,
                it.arguments?.getString("name"),
                it.arguments?.getString("phone")
            )
        }
        composable(route = Bookmark.CustomerInfo.name)
        { InfoUI(navController = navController) }
        composable(route = Bookmark.CustomerReservation.name)
        { ReservationUI(navController = navController) }
        composable(route = Bookmark.CustomerConfirmed.name)
        {ConfirmedUI(navController = navController)}
    }
}