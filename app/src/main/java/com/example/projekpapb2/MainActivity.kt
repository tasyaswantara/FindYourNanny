package com.example.projekpapb2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
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
import com.example.projekpapb2.ui.screens.AnimalCaretakerScreen
import com.example.projekpapb2.ui.screens.BabyCaretakerScreen
import com.example.projekpapb2.ui.screens.ElderlyCaretakerScreen
import com.example.projekpapb2.ui.screens.NotificationScreen
import com.example.projekpapb2.ui.screens.OnBoardingInterface
import com.example.projekpapb2.ui.screens.OnBoardingPage
import com.example.projekpapb2.ui.screens.ReviewScreen
import com.example.projekpapb2.ui.screens.PilihJadwalScreen
import com.google.firebase.auth.FirebaseAuth


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        setContent {
            val isLoggedIn = FirebaseAuth.getInstance().currentUser != null
            val startDestination = if (isLoggedIn) "home" else "onboarding"
            FindYourNannyApp(startDestination)
        }
    }
}

@Composable
fun FindYourNannyApp(startDestination: String) {
    val navController = rememberNavController()
    val authRepository = AuthRepository()
    val nannyRepository = NannyRepository()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("login") {
            LoginScreen(navController = navController, authRepository = authRepository)
        }
        composable("register") {
            RegisterScreen(navController = navController, authRepository = authRepository)
        }
        composable("pilih/{nannyId}") {  backStackEntry ->
            val nannyId = backStackEntry.arguments?.getString("nannyId") ?: ""
            PilihJadwalScreen(nannyId = nannyId, navController = navController, repository = nannyRepository)
        }

        composable("onboarding") {
            OnBoardingPage(
                navController = navController, page = OnBoardingInterface(
                    title = "Selamat datang di Find Your Nanny!",
                    description = "Aplikasi Find Your Nanny memudahkan Anda menemukan tenaga bantuan rumah tangga yang berpengalaman dan terpercaya.",
                    image = R.drawable.intro_satu
                )
            )
        }
        composable("home") {
            HomeScreen(navController = navController, repository = nannyRepository)
        }
        composable("elderlyList") {
            ElderlyCaretakerScreen(navController = navController, repository = nannyRepository)
        }
        composable("animalList") {
            AnimalCaretakerScreen(navController = navController, repository = nannyRepository)
        }
        composable("homeList") {
            HouseCaretakerScreen(navController = navController, repository = nannyRepository)
        }
        composable("babyList") {
            BabyCaretakerScreen(navController = navController, repository = nannyRepository)
        }
        composable("detail/{nannyId}") { backStackEntry ->
            val nannyId = backStackEntry.arguments?.getString("nannyId") ?: ""
            DetailScreen(
                nannyId = nannyId,
                navController = navController,
                repository = nannyRepository
            )
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
        composable("notifikasi") {
            NotificationScreen(navController = navController)
        }
        composable("history") {
            HistoryScreen(navController = navController)
        }
        composable("review") {
            ReviewScreen(navController = navController)
        }
    }
}
