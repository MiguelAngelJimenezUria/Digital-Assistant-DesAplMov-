package com.shrimpdevs.digitalassistant

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.shrimpdevs.digitalassistant.screens.HomeScreen
import com.shrimpdevs.digitalassistant.screens.presentation.InitialScreen
import com.shrimpdevs.digitalassistant.screens.presentation.LoginScreen
import com.shrimpdevs.digitalassistant.screens.presentation.SignUpScreen

@Composable
fun NavigationWrapper(
    navHostController: NavHostController,
    auth: FirebaseAuth,
    db: FirebaseFirestore
) {

    NavHost(navController = navHostController, startDestination = "initial") {
        composable("initial") {
            InitialScreen(
                navigateToLogin = { navHostController.navigate("logIn") },
                navigateToSignUp = { navHostController.navigate("signUp") }
            )
        }
        composable("logIn") {
            LoginScreen(
                navigateToInitial = { navHostController.navigate("initial") },
                navigateToHome = { navHostController.navigate("home") },
                auth
            )
        }
        composable("signUp") {
            SignUpScreen(
                navigateToInitial = { navHostController.navigate("initial") },
                navigateToHome = { navHostController.navigate("home") },
                auth)
        }
        composable("home"){
            HomeScreen(db)
        }
    }
}