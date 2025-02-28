package com.example.progo.ui.screens.workoutScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.progo.data.entities.ExerciseRecord
import com.example.progo.ui.component.ProgoTopBar
import com.example.progo.ui.screens.informationScreens.PastReps
import com.example.progo.ui.screens.informationScreens.PastWeight
import com.example.progo.ui.screens.informationScreens.SetNumber
import com.example.progo.ui.viewmodel.ExerciseRoutineViewModel

@Composable
fun ExerciseStatsScreen(
    navController: NavController,
    exerciseName: String,
    viewModel: ExerciseRoutineViewModel
){
    viewModel.getExerciseRecords(exerciseName)
    val recordsList by viewModel.exerciseRecords.collectAsState()
    Scaffold(
        topBar = { ProgoTopBar(navController = navController)}
    ) {
        ExerciseStatsContent(
            exerciseName = exerciseName,
            paddingValues = it,
            viewModel = viewModel,
            recordsList = recordsList
        )
    }
}

@Composable
fun ExerciseStatsContent(
    exerciseName: String,
    paddingValues: PaddingValues,
    viewModel: ExerciseRoutineViewModel,
    recordsList: List<ExerciseRecord>
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        item {
            Row {
                Spacer(Modifier.size(10.dp))
                Text(exerciseName, fontSize = 30.sp)
            }
        }
        items(recordsList){ item ->
            ExerciseRegister(item = item)
        }
    }
}

@Composable
fun ExerciseRegister(
    item: ExerciseRecord
){
    Column {
        Spacer(Modifier.size(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.secondary),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.size(10.dp))
            Text(item.date)
            Spacer(modifier = Modifier.size(30.dp))
            Text("RM: ${item.rm}")
        }
        Spacer(Modifier.size(10.dp))
        Row() {
            Spacer(Modifier.size(30.dp))
            SetNumber(item)
            Spacer(Modifier.size(30.dp))
            PastWeight(item)
            Spacer(Modifier.size(50.dp))
            PastReps(item)
        }
        Spacer(Modifier.size(10.dp))
    }
}