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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.progo.data.entities.routine
import com.example.progo.ui.component.PrincipalButton
import com.example.progo.ui.navigationScreens.Graph
import com.example.progo.ui.viewmodel.ExerciseRoutineViewModel

@Composable
fun WorkoutScreen(navController: NavController, paddingValues: PaddingValues, viewModel: ExerciseRoutineViewModel){

    val auxRoutineList by viewModel.allRoutines.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = PaddingValues(10.dp)
    ) {
        item {
            CreateNewRoutineButton(navController = navController)
        }
        items(auxRoutineList){ item ->
            RoutinePreVisualization(item, viewModel = viewModel, navController = navController)
        }
    }
}

@Composable
fun RoutinePreVisualization(item: routine, viewModel: ExerciseRoutineViewModel, navController: NavController){
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
                Text(
                    text = item.routineName,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                AdditionalOptions(viewModel = viewModel, item = item, navController = navController)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text("Empezar Rutina")
                }
            }
        }
    }
}

@Composable
fun CreateNewRoutineButton(navController: NavController){
    PrincipalButton(
        text = "Crear nueva rutina",
        onClick = {
            navController.navigate(Graph.WORKOUT)
        },
        height = 80,
        width = 360,
    )
}

@Composable
fun AdditionalOptions(viewModel: ExerciseRoutineViewModel, item: routine, navController: NavController){
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = "Más opciones", tint = MaterialTheme.colorScheme.onSecondary)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(onClick = { viewModel.deleteRoutineWithCrossRef(item)}, text = {Text("Eliminar")})
            DropdownMenuItem(
                onClick = {},
                text = { Text("Editar")}
            )
        }
    }
}