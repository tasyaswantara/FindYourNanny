package com.example.projekpapb2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.projekpapb2.ui.screens.BabyCaretakerScreen
import com.example.projekpapb2.ui.screens.DetailScreen
import com.example.projekpapb2.data.repository.NannyRepository
import com.example.projekpapb2.ui.screens.AnimalCaretakerScreen
import com.example.projekpapb2.ui.screens.ElderlyCaretakerScreen
import com.example.projekpapb2.ui.screens.HouseCaretakerScreen

object NavRoutes {
    const val NANNY_LIST = "nannyList"
    const val DETAIL = "detail/{nannyId}"
    fun detailRoute(nannyId: String, service: String): String = "detail/$nannyId/$service"
}

@Composable
fun NavGraph(navController: NavHostController) {
    val repository = NannyRepository()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.NANNY_LIST
    ) {
        composable(NavRoutes.NANNY_LIST) {
            BabyCaretakerScreen(navController = navController, repository = repository)
        }

        composable(
            route = NavRoutes.DETAIL,
            arguments = listOf(navArgument("nannyId") { defaultValue = "" })
        ) { backStackEntry ->
            val nannyId = backStackEntry.arguments?.getString("nannyId").orEmpty()
            val service = backStackEntry.arguments?.getString("service").orEmpty()
            DetailScreen(navController = navController, nannyId = nannyId, repository = repository, service=service)
        }

        composable("elderlyCaretakerList") {
            ElderlyCaretakerScreen(
                navController = navController,
                repository = NannyRepository()
            )
        }
        composable("animalCaretakerList") {
            AnimalCaretakerScreen(
                navController = navController,
                repository = NannyRepository()
            )
        }
        composable("houseCaretakerList") {
            HouseCaretakerScreen(
                navController = navController,
                repository = NannyRepository()
            )
        }



    }
}
