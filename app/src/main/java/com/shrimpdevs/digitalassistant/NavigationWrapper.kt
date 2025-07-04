package com.shrimpdevs.digitalassistant

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.shrimpdevs.digitalassistant.models.Event
import com.shrimpdevs.digitalassistant.screens.HomeScreen
import com.shrimpdevs.digitalassistant.screens.event.CreateEvent
import com.shrimpdevs.digitalassistant.screens.event.EditEvent
import com.shrimpdevs.digitalassistant.screens.event.EventScreen
import com.shrimpdevs.digitalassistant.screens.presentation.InitialScreen
import com.shrimpdevs.digitalassistant.screens.presentation.LoginScreen
import com.shrimpdevs.digitalassistant.screens.presentation.SignUpScreen
import com.shrimpdevs.digitalassistant.screens.settings.SettingsScreen

@Composable
fun NavigationWrapper(
    navHostController: NavHostController,
    auth: FirebaseAuth,
    db: FirebaseFirestore
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (auth.currentUser != null) {
            navHostController.navigate("event") {
                popUpTo("initial") { inclusive = true }
            }
        }
    }

    NavHost(navController = navHostController, startDestination = "initial") {
        composable("initial") {
            InitialScreen(
                navigateToLogin = { navHostController.navigate("logIn") },
                navigateToSignUp = { navHostController.navigate("signUp") },
                navigateToEvent = {
                    navHostController.navigate("event") {
                        popUpTo("initial") { inclusive = true }
                    }
                },
                auth = auth,
                context = context
            )
        }
        composable("logIn") {
            LoginScreen(
                navigateToInitial = { navHostController.navigate("initial") },
                navigateToEvent = { navHostController.navigate("event") },
                auth
            )
        }
        composable("signUp") {
            SignUpScreen(
                navigateToInitial = { navHostController.navigate("initial") },
                navigateToEvent = { navHostController.navigate("event") },
                auth)
        }
        composable("home"){
            HomeScreen(db)
        }
        composable("event") {
            EventScreen(
                db = db,
                navigateToCreateEvent = { navHostController.navigate("CreateEvent") },
                navigateToInitial = { navHostController.navigate("initial") },
                navigateToSettings = { navHostController.navigate("Settings") },
                onEventClick = { event: Event ->
                    navHostController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "event",
                        value = event
                    )
                    navHostController.navigate("EditEvent")
                }
            )
        }
        composable ("CreateEvent"){
            CreateEvent(
                db,
                navigateToEvent = { navHostController.navigate("event") },
                context = LocalContext.current
            )
        }
        composable("editEvent") {
            val event = navHostController.previousBackStackEntry?.savedStateHandle?.get<Event>("event")
            event?.let {
                EditEvent(
                    db = db,
                    event = it,
                    navigateBack = { navHostController.navigate("event") }
                )
            }
        }
        composable("Settings") {
            SettingsScreen(
                navigateBack = { navHostController.navigate("event") },
                navHostController = navHostController
            )
        }
    }
}