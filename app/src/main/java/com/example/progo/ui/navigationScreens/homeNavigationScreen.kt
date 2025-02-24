package com.example.progo.ui.navigationScreens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.runtime.getValue
import com.example.progo.ui.screens.workoutScreens.ExerciseListScreen
import com.example.progo.ui.screens.workoutScreens.NewExerciseScreen
import com.example.progo.ui.viewmodel.ExerciseRoutineSharedViewModel

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
            val sharedViewModel = entry.sharedViewModel<ExerciseRoutineSharedViewModel>(navController = navController)
            val routineState by sharedViewModel.routineStarted.collectAsStateWithLifecycle()
            WorkoutScreen(
                navController = navController,
                paddingValues = paddingValues,
                viewModel = viewModel,
                sharedViewModel = sharedViewModel,
                routineState = routineState
            )
        }
        composable(route = navigationItems.Information.route) {
            InformationScreen(paddingValues = paddingValues)
        }
        composable(route = OnWorkOutScreens.onWorkOutScreen.route){ entry ->
            val sharedViewModel = entry.sharedViewModel<ExerciseRoutineSharedViewModel>(navController = navController)
            val routineName by sharedViewModel.sharedRoutineName.collectAsStateWithLifecycle()
            val exerciseList by sharedViewModel.sharedExerciseList.collectAsStateWithLifecycle()
            val sets by sharedViewModel.sharedExerciseSetsList.collectAsStateWithLifecycle()
            val repsValues by sharedViewModel.sharedRepsValue.collectAsStateWithLifecycle()
            val weightValues by sharedViewModel.sharedWeightValue.collectAsStateWithLifecycle()
            val routineState by sharedViewModel.routineStarted.collectAsStateWithLifecycle()
            OnWorkOutScreen(
                navController = navController,
                routineName = routineName,
                exerciseList = exerciseList,
                sharedViewModel = sharedViewModel,
                viewModel = viewModel,
                sets = sets,
                repsValues = repsValues,
                weightValues = weightValues,
                routineState = routineState
            )
        }

        composable(route = OnWorkOutScreens.onWorkOutExerciseList.route){ entry ->
            val sharedViewModel = entry.sharedViewModel<ExerciseRoutineSharedViewModel>(navController = navController)
            ExerciseListScreen(
                navController = navController,
                viewModel = viewModel,
                sharedViewModel = sharedViewModel,
                navRoute = OnWorkOutScreens.onWorkOutCreateExercise.route
            )
        }

        composable(route = OnWorkOutScreens.onWorkOutCreateExercise.route){
            NewExerciseScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        workoutNavGraph(navController = navController, viewModel = viewModel)
    }
}

sealed class OnWorkOutScreens(val route: String){
    object onWorkOutScreen: OnWorkOutScreens(route = "on_workout")
    object onWorkOutExerciseList: OnWorkOutScreens(route = "on_workout_exercise_list")
    object onWorkOutCreateExercise: OnWorkOutScreens(route = "on_workout_create_exercise")
}