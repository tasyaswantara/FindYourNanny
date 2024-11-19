package com.example.projekpapb2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.projekpapb2.ui.screens.NannyScreen
import com.example.projekpapb2.ui.screens.DetailScreen
import com.example.projekpapb2.data.repository.NannyRepository

object NavRoutes {
    const val NANNY_LIST = "nannyList"
    const val DETAIL = "detail/{nannyId}"
    fun detailRoute(nannyId: String): String = "detail/$nannyId"
}

@Composable
fun NavGraph(navController: NavHostController) {
    val repository = NannyRepository()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.NANNY_LIST
    ) {
        composable(NavRoutes.NANNY_LIST) {
            NannyScreen(navController = navController, repository = repository)
        }

        composable(
            route = NavRoutes.DETAIL,
            arguments = listOf(navArgument("nannyId") { defaultValue = "" })
        ) { backStackEntry ->
            val nannyId = backStackEntry.arguments?.getString("nannyId").orEmpty()
            DetailScreen(navController = navController, nannyId = nannyId, repository = repository)
        }
    }
}
