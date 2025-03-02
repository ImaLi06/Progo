package com.example.progo.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.progo.ui.navigationScreens.OnWorkOutScreens
import com.example.progo.ui.viewmodel.ExerciseRoutineSharedViewModel

@Composable
fun BottomBarActualRoutine(
    routineName: String,
    navController: NavController,
    sharedViewModel: ExerciseRoutineSharedViewModel
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.secondary),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Spacer(modifier = Modifier.size(20.dp))
            RoutineNameBottomBar(routineName)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            DiscardRoutine(sharedViewModel = sharedViewModel)
            Spacer(modifier = Modifier.size(15.dp, 90.dp))
            ContinueRoutine(
                navController = navController,
                routineName = routineName,
                sharedViewModel = sharedViewModel
            )
            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun RoutineNameBottomBar(routineName: String){
    Text(
        text = routineName,
        fontSize = 25.sp,
        color = Color.Green
    )
}

@Composable
fun DiscardRoutine(sharedViewModel: ExerciseRoutineSharedViewModel){
    IconButton(
        onClick = {
            sharedViewModel.changeRoutineState(false)
            sharedViewModel.deleteLastTitle()
        }
    ) {
        Icon(Icons.Default.Clear, contentDescription = "discard", tint = MaterialTheme.colorScheme.onSecondary)
    }
}

@Composable
fun ContinueRoutine(
    navController: NavController,
    sharedViewModel: ExerciseRoutineSharedViewModel,
    routineName: String
){
    IconButton(
        onClick = {
            sharedViewModel.addTitle(routineName)
            navController.navigate(OnWorkOutScreens.onWorkOutScreen.route)
        }
    ) {
        Icon(Icons.Default.Check, contentDescription = "continue", tint = MaterialTheme.colorScheme.onSecondary)
    }
}