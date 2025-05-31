package com.shrimpdevs.digitalassistant

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shrimpdevs.digitalassistant.screens.presentation.InitialScreen
import com.shrimpdevs.digitalassistant.screens.presentation.LoginScreen
import com.shrimpdevs.digitalassistant.screens.presentation.SignUpScreen

@Composable
fun NavigationWrapper(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination= "Initial"){
        composable("Initial"){
            InitialScreen()
        }

        composable("Login"){
            LoginScreen()
        }

        composable("SignUp"){
            SignUpScreen()
        }

    }
}