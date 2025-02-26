package com.example.progo.ui.screens.informationScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.progo.data.JsonConverters
import com.example.progo.data.entities.ExerciseRecord
import com.example.progo.ui.component.ProgoTopBar
import com.example.progo.ui.component.SecondaryTextTemplate
import com.example.progo.ui.viewmodel.ExerciseRoutineViewModel
import com.google.common.base.Converter

@Composable
fun PastWorkoutScreen(
    navController: NavController,
    viewModel: ExerciseRoutineViewModel,
    routineName: String,
){
    val pastRoutineExercises by viewModel.pastRoutine.collectAsState()
    Scaffold(
        topBar = { ProgoTopBar(navController)}
    ) {
        PastWorkoutScreenContent(
            paddingValues = it,
            routineName = routineName,
            pastRoutineExercises = pastRoutineExercises
        )
    }
}

@Composable
fun PastWorkoutScreenContent(
    paddingValues: PaddingValues,
    routineName: String,
    pastRoutineExercises: List<ExerciseRecord>
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = PaddingValues(10.dp),
    ) {
        item {
            Text(routineName, fontSize = 30.sp)
        }
        items(pastRoutineExercises){ item ->
            Record(item = item)
        }
    }
}

@Composable
fun Record(item: ExerciseRecord){
    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .width(400.dp),
    ) {
        Spacer(Modifier.size(10.dp))
        Row {
            Spacer(Modifier.size(15.dp))
            SecondaryTextTemplate(secondaryText = item.exerciseName, fontSize = 25)
        }
        Spacer(Modifier.size(20.dp))
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

@Composable
fun SetNumber(item: ExerciseRecord){
    Column {
        Text(" ")
        Spacer(Modifier.size(10.dp))
        repeat(item.sets){i ->
            Text("${i + 1}", fontSize = 20.sp)
        }
    }
}

@Composable
fun PastReps(item: ExerciseRecord){
    val converter = JsonConverters()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Reps", fontSize = 20.sp)
        Spacer(Modifier.size(10.dp))
        val repsList = converter.fromJsonToIntList(item.reps)
        repeat(item.sets){i ->
            Text("${repsList[i]}", fontSize = 20.sp)
        }
    }
}

@Composable
fun PastWeight(item: ExerciseRecord){
    val converter = JsonConverters()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Weight", fontSize = 20.sp)
        Spacer(Modifier.size(10.dp))
        val weightList = converter.fromJsonToFloatList(item.weight)
        repeat(item.sets){i->
            Text("${weightList[i]}", fontSize = 20.sp)
        }
    }
}