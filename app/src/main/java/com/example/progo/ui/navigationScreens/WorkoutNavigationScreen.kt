package com.example.progo.ui.navigationScreens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.progo.ui.screens.workoutScreens.ExerciseListScreen
import com.example.progo.ui.screens.workoutScreens.ExerciseStatsScreen
import com.example.progo.ui.screens.workoutScreens.NewExerciseScreen
import com.example.progo.ui.screens.workoutScreens.NewWorkoutScreen
import com.example.progo.ui.viewmodel.ExerciseRoutineSharedViewModel
import com.example.progo.ui.viewmodel.ExerciseRoutineViewModel

fun NavGraphBuilder.workoutNavGraph(
    navController: NavHostController,
    viewModel: ExerciseRoutineViewModel,
) {
    navigation(
        route = Graph.WORKOUT,
        startDestination = WorkoutScreen.NewWorkout.route,
    ) {
        composable(route = WorkoutScreen.NewWorkout.route) { entry ->
            val sharedViewModel = entry.sharedViewModel<ExerciseRoutineSharedViewModel>(navController = navController)
            val state by sharedViewModel.sharedExerciseList.collectAsStateWithLifecycle()
            val name by sharedViewModel.sharedRoutineName.collectAsStateWithLifecycle()
            val sets by sharedViewModel.sharedExerciseSetsList.collectAsStateWithLifecycle()
            val repsValues by sharedViewModel.sharedRepsValue.collectAsStateWithLifecycle()
            val weightValues by sharedViewModel.sharedWeightValue.collectAsStateWithLifecycle()
            val screenType by sharedViewModel.workoutScreenType.collectAsStateWithLifecycle()

            NewWorkoutScreen(
                navController = navController,
                viewModel = viewModel,
                sharedViewModel = sharedViewModel,
                exerciseList = state,
                routineName = name,
                repsValues = repsValues,
                weightValues = weightValues,
                sets = sets,
                screenType = screenType
            )
        }
        composable(route = WorkoutScreen.ExerciseList.route) { entry ->
            val sharedViewModel = entry.sharedViewModel<ExerciseRoutineSharedViewModel>(navController = navController)

            ExerciseListScreen(
                navController = navController,
                viewModel = viewModel,
                sharedViewModel = sharedViewModel,
                navRoute = WorkoutScreen.NewExercise.route
            )
        }
        composable(route = WorkoutScreen.NewExercise.route) { entry ->
            val sharedViewModel = entry.sharedViewModel<ExerciseRoutineSharedViewModel>(navController = navController)

            NewExerciseScreen(
                navController = navController,
                viewModel = viewModel,
                sharedViewModel = sharedViewModel
            )
        }

        composable(route = WorkoutScreen.ExerciseStats.route){ entry ->
            val sharedViewModel = entry.sharedViewModel<ExerciseRoutineSharedViewModel>(navController = navController)
            ExerciseStatsScreen(
                navController = navController,
                viewModel = viewModel,
                sharedViewModel = sharedViewModel
            )
        }
    }
}

sealed class WorkoutScreen(val route: String) {
    object NewWorkout : WorkoutScreen(route = "new_workout")
    object ExerciseList : WorkoutScreen(route = "exercise_list")
    object NewExercise : WorkoutScreen(route = "new_exercise")
    object ExerciseStats : WorkoutScreen(route = "exercise_stats")
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}