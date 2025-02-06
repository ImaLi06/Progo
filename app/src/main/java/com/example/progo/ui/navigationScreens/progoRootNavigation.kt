package com.example.progo.ui.navigationScreens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.progo.ui.screens.HomeScreen
import com.example.progo.ui.viewmodel.ExerciseRoutineViewModel

@Composable
fun ProgoRootNavigation(navController: NavHostController, viewModel: ExerciseRoutineViewModel) {
    NavHost(navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.LOGIN
    ) {
        authNavGraph(navController = navController)
        composable(route = Graph.HOME){
            HomeScreen(viewModel = viewModel)
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val LOGIN = "auth_graph"
    const val HOME = "home_graph"
    const val WORKOUT = "workout_graph"
}