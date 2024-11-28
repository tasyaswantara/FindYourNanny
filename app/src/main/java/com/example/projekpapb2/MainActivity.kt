package com.example.projekpapb2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projekpapb2.data.repository.AuthRepository
import com.example.projekpapb2.data.repository.NannyRepository
import com.example.projekpapb2.ui.screens.AccountInformationScreen
import com.example.projekpapb2.ui.screens.BookingScreen
import com.example.projekpapb2.ui.screens.DetailScreen
import com.example.projekpapb2.ui.screens.HistoryScreen
import com.example.projekpapb2.ui.screens.HomeScreen
import com.example.projekpapb2.ui.screens.HouseCaretakerScreen
import com.example.projekpapb2.ui.screens.LoginScreen
import com.example.projekpapb2.ui.screens.ProfileScreen
import com.example.projekpapb2.ui.screens.RegisterScreen
import com.google.firebase.FirebaseApp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        val splashScreen =  installSplashScreen()
        splashScreen.setKeepOnScreenCondition{true}

        CoroutineScope(Dispatchers.Main).launch {
            delay(3000L)
            splashScreen.setKeepOnScreenCondition{false}
        }
        setContent {
            FindYourNannyApp()
        }
    }
}

@Composable
fun FindYourNannyApp() {
    val navController = rememberNavController()
    val authRepository = AuthRepository()
    val nannyRepository = NannyRepository()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(navController = navController, authRepository = authRepository)
        }
        composable("register") {
            RegisterScreen(navController = navController, authRepository = authRepository)
        }
        composable("home") {
            HomeScreen(navController = navController, repository = nannyRepository)
        }
        composable("detail/{nannyId}") { backStackEntry ->
            val nannyId = backStackEntry.arguments?.getString("nannyId") ?: ""
            DetailScreen(nannyId = nannyId, navController = navController, repository = nannyRepository)
        }
        composable("booking/{nannyId}") { backStackEntry ->
            BookingScreen(navController = navController)
        }
        composable("profil") {
            ProfileScreen(navController = navController)
        }
        composable("accountinfo") {
            AccountInformationScreen(navController = navController)
        }
        composable("history") {
            HistoryScreen(navController = navController)
        }
    }
}
