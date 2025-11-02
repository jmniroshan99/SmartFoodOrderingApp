package com.example.smartfoodorderingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartfoodorderingapp.ui.screens.*
import com.example.smartfoodorderingapp.ui.theme.SmartFoodTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

/**
 * Main entry point of SmartFoodOrderingApp
 * Initializes Firebase and sets up the navigation graph.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        setContent {
            SmartFoodTheme {
                val navController = rememberNavController()
                SmartFoodApp(navController = navController)
            }
        }
    }
}

/**
 * Root composable that holds the Navigation Graph.
 */
@Composable
fun SmartFoodApp(navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()
    var startDestination by remember { mutableStateOf("splash") }

    // Determine start destination based on login state
    LaunchedEffect(Unit) {
        delay(1500)
        startDestination = if (auth.currentUser != null) "home" else "login"
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Splash
        composable("splash") { SplashScreen(navController) }

        // Authentication
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }

        // Main User Flow
        composable("home") { HomeScreen(navController) }
        composable("menu") { MenuScreen(navController) }
        composable("cart") { CartScreen(navController) }
        composable("checkout") { CheckoutScreen(navController) }
        composable("otp") { OtpScreen(navController) }
        composable("orderStatus") { OrderStatusScreen(navController) }

        // Admin
        composable("admin") { AdminScreen(navController) }
    }
}
