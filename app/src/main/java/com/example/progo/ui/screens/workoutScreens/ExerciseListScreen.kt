package com.example.progo.ui.screens.workoutScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.progo.data.entities.Exercise
import com.example.progo.ui.component.PrincipalButton
import com.example.progo.ui.component.ProgoTopBar
import com.example.progo.ui.component.SecondaryButton
import com.example.progo.ui.viewmodel.ExerciseRoutineSharedViewModel
import com.example.progo.ui.viewmodel.ExerciseRoutineViewModel

@Composable
fun ExerciseListScreen(
    navController: NavController,
    viewModel: ExerciseRoutineViewModel,
    sharedViewModel: ExerciseRoutineSharedViewModel,
    navRoute: String
){
    Scaffold(
        topBar = { ProgoTopBar(navController, sharedViewModel, "list") }
    ) {
        ExerciseListContent(
            paddingValues = it,
            navController = navController,
            viewModel = viewModel,
            sharedViewModel = sharedViewModel,
            navRoute = navRoute
        )
    }
}

@Composable
fun ExerciseListContent(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: ExerciseRoutineViewModel,
    sharedViewModel: ExerciseRoutineSharedViewModel,
    navRoute: String
){
    val existentExerciseList by viewModel.allExercises.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = PaddingValues(all = 20.dp)
    ) {
        items(existentExerciseList){
            item -> Exercises(
                item = item,
                sharedViewModel = sharedViewModel,
                navController = navController,
            )
        }
        item {
            PrincipalButton(
                text = "Agregar",
                onClick = {
                    sharedViewModel.addTitle("create_exercise")
                    navController.navigate(navRoute)
                },
                height = 50,
                width = 350
            )
        }
    }
}

@Composable
fun Exercises(
    item: Exercise,
    sharedViewModel: ExerciseRoutineSharedViewModel,
    navController: NavController,
){
    SecondaryButton(
        text = item.exerciseName,
        onClick = {
            sharedViewModel.deleteLastTitle()
            sharedViewModel.addExercise(item)
            navController.popBackStack()
        },
        width = 350,
        height = 50
    )
}
