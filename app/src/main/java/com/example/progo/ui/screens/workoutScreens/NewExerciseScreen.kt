package com.example.progo.ui.screens.workoutScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.progo.data.entities.exercise
import com.example.progo.ui.component.PrincipalButton
import com.example.progo.ui.component.PrincipalTextLabel
import com.example.progo.ui.component.ProgoTopBar
import com.example.progo.ui.viewmodel.ExerciseRoutineViewModel

@Composable
fun NewExerciseScreen(navController: NavController, viewModel: ExerciseRoutineViewModel){
    Scaffold(
        topBar = {ProgoTopBar(navController = navController)}
    ) {
        NewExerciseContent(paddingValues = it, viewModel = viewModel, navController = navController)
    }
}

@Composable
fun NewExerciseContent(paddingValues: PaddingValues, viewModel: ExerciseRoutineViewModel, navController: NavController){
    var exerciseNameAux by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        PrincipalTextLabel(
            value = exerciseNameAux,
            onValueChange = {exerciseNameAux = it},
            height = 60,
            width = 350
        )
        Spacer(modifier = Modifier.height(30.dp))
        PrincipalButton(
            text = "Listo",
            onClick = {
                val trimExerciseName = exerciseNameAux.trim()
                if(trimExerciseName.isNotEmpty()){
                    viewModel.addExercise(exercise(exerciseName = exerciseNameAux))
                    exerciseNameAux = ""
                    navController.popBackStack()
                }
                      },
            height = 50,
            width = 350
        )
    }
}