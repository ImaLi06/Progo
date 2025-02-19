package com.example.progo.ui.navigationScreens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.progo.data.navigationItems
import com.example.progo.ui.screens.informationScreens.InformationScreen
import com.example.progo.ui.screens.profileScreens.ProfileScreen
import com.example.progo.ui.screens.workoutScreens.OnWorkOutScreen
import com.example.progo.ui.screens.workoutScreens.WorkoutScreen
import com.example.progo.ui.viewmodel.ExerciseRoutineViewModel
import com.example.progo.ui.viewmodel.HomeSharedViewModel
import androidx.compose.runtime.getValue

@Composable
fun HomeNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: ExerciseRoutineViewModel
) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = navigationItems.Profile.route
    ) {
        composable(route = navigationItems.Profile.route) {
            ProfileScreen(paddingValues = paddingValues)
        }
        composable(route = navigationItems.Workout.route) {entry ->
            val homeSharedViewModel = entry.sharedViewModel<HomeSharedViewModel>(navController = navController)
            WorkoutScreen(
                navController = navController,
                paddingValues = paddingValues,
                viewModel = viewModel,
                homeSharedViewModel = homeSharedViewModel
            )
        }
        composable(route = navigationItems.Information.route) {
            InformationScreen(paddingValues = paddingValues)
        }
        composable(route = OnWorkOutScreens.onWorkOutScreen.route){ entry ->
            val homeSharedViewModel = entry.sharedViewModel<HomeSharedViewModel>(navController = navController)
            val routineName by homeSharedViewModel.actualRoutineName.collectAsStateWithLifecycle()
            val exerciseList by homeSharedViewModel.sharedExerciseList.collectAsStateWithLifecycle()
            OnWorkOutScreen(
                navController = navController,
                routineName = routineName,
                exerciseList = exerciseList,
                homeSharedViewModel = homeSharedViewModel,
                viewModel = viewModel
            )
        }
        workoutNavGraph(navController = navController, viewModel = viewModel)
    }
}

sealed class OnWorkOutScreens(val route: String){
    object onWorkOutScreen: OnWorkOutScreens(route = "on_workout")
}