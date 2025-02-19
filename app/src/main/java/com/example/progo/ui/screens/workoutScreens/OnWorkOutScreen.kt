package com.example.progo.ui.screens.workoutScreens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.progo.data.entities.Exercise
import com.example.progo.ui.component.ProgoTopBar
import com.example.progo.ui.viewmodel.ExerciseRoutineViewModel
import com.example.progo.ui.viewmodel.HomeSharedViewModel

@Composable
fun OnWorkOutScreen(
    navController: NavController,
    routineName: String,
    exerciseList: List<Exercise>,
    homeSharedViewModel: HomeSharedViewModel,
    viewModel: ExerciseRoutineViewModel
){
    val auxExerciseList by viewModel.actualRoutine.collectAsState()
    homeSharedViewModel.defineExerciseList(auxExerciseList)
    Scaffold(
        topBar = { ProgoTopBar(navController)}
    ) {
        OnWorkOutScreenContent(
            paddingValues = it,
            routineName = routineName,
            exerciseList = exerciseList,
            homeSharedViewModel = homeSharedViewModel
        )
    }
}

@Composable
fun OnWorkOutScreenContent(
    paddingValues: PaddingValues,
    routineName: String,
    exerciseList: List<Exercise>,
    homeSharedViewModel: HomeSharedViewModel
){
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        item { Text(routineName) }
        items(exerciseList){exercise ->
            Text(exercise.exerciseName)
        }
    }
}

@Composable
fun InputOnWorkOut(exercise: Exercise){
    Text(exercise.exerciseName)
}