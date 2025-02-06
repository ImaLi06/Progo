package com.example.progo.ui.navigationScreens

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.progo.ui.screens.loginScreens.insertUserData

fun NavGraphBuilder.authNavGraph(navController: NavHostController){
    navigation(
        route = Graph.LOGIN,
        startDestination = loginScreen.Login.route
    ){
        composable(route = loginScreen.Login.route) {
            insertUserData(
                start = {
                    navController.popBackStack()
                    navController.navigate(Graph.HOME)
                }
            )
        }
    }
}

sealed class loginScreen(val route: String){
    object Login: loginScreen("login_screen")
}