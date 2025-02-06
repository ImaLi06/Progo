package com.example.progo.ui.screens.workoutScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.progo.data.entities.exercise
import com.example.progo.data.entities.routine
import com.example.progo.ui.component.PrincipalTextLabel
import com.example.progo.ui.component.PrincipalButton
import com.example.progo.ui.component.SecondaryTextLabel
import com.example.progo.ui.component.SecondaryTextTemplate
import com.example.progo.ui.component.ProgoTopBar
import com.example.progo.ui.navigationScreens.WorkoutScreen
import com.example.progo.ui.viewmodel.ExerciseRoutineSharedViewModel
import com.example.progo.ui.viewmodel.ExerciseRoutineViewModel

@Composable
fun MainWorkoutScreen(
    navController: NavController,
    viewModel: ExerciseRoutineViewModel,
    exerciseList: List<exercise>,
    routineName: String,
    sharedViewModel: ExerciseRoutineSharedViewModel
){
    Scaffold(
        topBar = { ProgoTopBar(navController)}
    ) {
        MainWorkoutContent(
            paddingValues = it,
            navController = navController,
            viewModel = viewModel,
            exerciseList = exerciseList,
            routineName = routineName,
            sharedViewModel = sharedViewModel
        )
    }
}

@Composable
fun MainWorkoutContent(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: ExerciseRoutineViewModel,
    exerciseList: List<exercise>,
    routineName: String,
    sharedViewModel: ExerciseRoutineSharedViewModel
){

    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        contentPadding = PaddingValues(all = 20.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        item {
            RoutineNameTextLabel(
                routineName,
                {sharedViewModel.updateText(it)},
                height = 50,
                width = 350
            )
        }
        items(exerciseList) {
            item -> Input(item)
        }
        item {
            AddExerciseButton(
                text = "Agregar Ejercicio",
                onClick = {navController.navigate(WorkoutScreen.exerciseList.route)},
                height = 60,
                width = 350
            )
        }
        item {
            PrincipalButton(
                text = "Agregar Rutina",
                onClick = {
                    val trimRoutineName = routineName.trim()
                    if(trimRoutineName.isNotEmpty()) {
                        viewModel.InsertRoutineWithExercises(
                            routine = routine(routineName = routineName),
                            exercises = exerciseList
                        )
                        navController.popBackStack()
                    }
                },
                height = 60,
                width = 350
            )
        }
    }
}

@Composable
fun Input(item: exercise){
    var aux: String
    Column(modifier = Modifier
        .clip(shape = RoundedCornerShape(15.dp))
        .background(MaterialTheme.colorScheme.secondary)
        .width(350.dp),
    ) {
        Spacer(modifier = Modifier.size(25.dp, 10.dp))
        Row{
            Spacer(modifier = Modifier.size(25.dp, 10.dp))
            SecondaryTextTemplate(item.exerciseName, 20)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.size(10.dp))
            Column(
                modifier = Modifier.width(50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("${item.weight} X ${item.reps}")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text("Peso")
                Spacer(modifier = Modifier.size(10.dp))
                SecondaryTextLabel("", {aux = it}, 30, 100)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text("Repeticiones")
                Spacer(modifier = Modifier.size(10.dp))
                SecondaryTextLabel("", {aux = it}, 30, 100)
            }
        }
        Spacer(modifier = Modifier.size(25.dp, 10.dp))
    }
}

@Composable
fun RoutineNameTextLabel(
    routineName: String,
    onValueChange: (String) -> Unit,
    height: Int,
    width: Int
){
    PrincipalTextLabel(
        value = routineName,
        onValueChange = onValueChange,
        height = height,
        width = width
    )
}

@Composable
fun AddExerciseButton(
    text: String,
    onClick: () -> Unit,
    height: Int,
    width: Int
){
    PrincipalButton(text = text,
        onClick = onClick,
        height = height,
        width = width
    )
}