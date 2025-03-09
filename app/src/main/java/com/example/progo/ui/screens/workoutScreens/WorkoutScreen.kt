package com.example.progo.ui.screens.workoutScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.progo.data.entities.Routine
import com.example.progo.ui.component.BottomBarActualRoutine
import com.example.progo.ui.component.PrincipalButton
import com.example.progo.ui.component.SecondaryText
import com.example.progo.ui.navigationScreens.Graph
import com.example.progo.ui.navigationScreens.OnWorkOutScreens
import com.example.progo.ui.viewmodel.ExerciseRoutineSharedViewModel
import com.example.progo.ui.viewmodel.ExerciseRoutineViewModel

@Composable
fun WorkoutScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: ExerciseRoutineViewModel,
    sharedViewModel: ExerciseRoutineSharedViewModel,
    routineState: Boolean,
    routineName: String
){

    val auxRoutineList by viewModel.allRoutines.collectAsState(initial = emptyList())

    Scaffold(
        bottomBar = {
            if(routineState){
                BottomBarActualRoutine(
                    navController = navController,
                    sharedViewModel = sharedViewModel,
                    routineName = routineName
                )
            }
        },
        modifier = Modifier.padding(paddingValues)
    ) {
        WorkoutScreenContent(
            paddingValues = it,
            navController = navController,
            auxRoutineList = auxRoutineList,
            routineState = routineState,
            viewModel = viewModel,
            sharedViewModel = sharedViewModel
        )
    }
}

@Composable
fun WorkoutScreenContent(
    paddingValues: PaddingValues,
    navController: NavController,
    auxRoutineList: List<Routine>,
    routineState: Boolean,
    viewModel: ExerciseRoutineViewModel,
    sharedViewModel: ExerciseRoutineSharedViewModel
){
    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = PaddingValues(10.dp)
    ) {
        item {
            CreateNewRoutineButton(
                navController = navController,
                sharedViewModel = sharedViewModel
            )
        }
        items(auxRoutineList){ item ->
            RoutinePreVisualization(
                item,
                viewModel = viewModel,
                navController = navController,
                sharedViewModel = sharedViewModel,
                routineState = routineState
            )
        }
    }
}

@Composable
fun RoutinePreVisualization(
    item: Routine,
    viewModel: ExerciseRoutineViewModel,
    navController: NavController,
    sharedViewModel: ExerciseRoutineSharedViewModel,
    routineState: Boolean
){
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.width(360.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                SecondaryText(
                    text = item.routineName,
                    fontSize = 20
                )
                WorkOutAdditionalOptions(
                    viewModel = viewModel,
                    item = item,
                    navController = navController,
                    sharedViewModel = sharedViewModel,
                    routineState = routineState
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                PrincipalButton(
                    text = "Empezar Rutina",
                    onClick = {
                        if(!routineState){
                            viewModel.getRoutineWithExercise(item.routineName)
                            viewModel.getSetsExerciseRoutine(item.routineName)
                            sharedViewModel.updateText(item.routineName)
                            sharedViewModel.addTitle(item.routineName)
                            sharedViewModel.changeWorkoutScreenType("workout")
                            navController.navigate(OnWorkOutScreens.OnWorkOutScreen.route)
                        }
                    },
                    height = 40,
                    width = 180
                )
            }
        }
    }
}

@Composable
fun CreateNewRoutineButton(navController: NavController, sharedViewModel: ExerciseRoutineSharedViewModel){
    PrincipalButton(
        text = "Crear nueva rutina",
        onClick = {
            sharedViewModel.addTitle("Edit")
            navController.navigate(Graph.WORKOUT)
        },
        height = 80,
        width = 360,
    )
}

@Composable
fun WorkOutAdditionalOptions(
    viewModel: ExerciseRoutineViewModel,
    item: Routine,
    navController: NavController,
    routineState: Boolean,
    sharedViewModel: ExerciseRoutineSharedViewModel
){
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = "Más opciones", tint = MaterialTheme.colorScheme.onSecondary)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(onClick = { viewModel.deleteRoutineWithCrossRef(item)}, text = {Text("Eliminar")})
            DropdownMenuItem(
                onClick = {
                    if(!routineState){
                        viewModel.getRoutineWithExercise(item.routineName)
                        viewModel.getSetsExerciseRoutine(item.routineName)
                        sharedViewModel.updateText(item.routineName)
                        sharedViewModel.addTitle(item.routineName)
                        sharedViewModel.changeWorkoutScreenType("edit")
                        navController.navigate(OnWorkOutScreens.OnWorkOutScreen.route)
                    }
                },
                text = { Text("Editar")}
            )
        }
    }
}