package com.example.progo.ui.navigationScreens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.progo.data.navigationItems
import com.example.progo.ui.screens.informationScreens.InformationScreen
import com.example.progo.ui.screens.profileScreens.ProfileScreen
import com.example.progo.ui.screens.workoutScreens.WorkoutScreen
import com.example.progo.ui.viewmodel.ExerciseRoutineViewModel
import com.example.progo.ui.viewmodel.HomeSharedViewModel

@Composable
fun HomeNavGraph(navController: NavHostController, paddingValues: PaddingValues, viewModel: ExerciseRoutineViewModel) {
    val homeSharedViewModel = navController.currentBackStackEntry?.sharedViewModel<HomeSharedViewModel>(navController)
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = navigationItems.Profile.route
    ) {
        composable(route = navigationItems.Profile.route) {
            ProfileScreen(paddingValues = paddingValues)
        }
        composable(route = navigationItems.Workout.route) {
            WorkoutScreen(navController = navController, paddingValues = paddingValues, viewModel = viewModel)
        }
        composable(route = navigationItems.Information.route) {
            InformationScreen(paddingValues = paddingValues)
        }
        if(homeSharedViewModel != null){
            workoutNavGraph(navController = navController, viewModel = viewModel, homeSharedViewModel = homeSharedViewModel)
        }
    }
}