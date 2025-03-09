package com.example.progo.ui.screens.informationScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.progo.data.entities.RoutineRecord
import com.example.progo.ui.component.BottomBarActualRoutine
import com.example.progo.ui.component.PrimaryText
import com.example.progo.ui.component.SecondaryText
import com.example.progo.ui.navigationScreens.OnWorkOutScreens
import com.example.progo.ui.viewmodel.ExerciseRoutineSharedViewModel
import com.example.progo.ui.viewmodel.ExerciseRoutineViewModel

@Composable
fun InformationScreen(
    paddingValues: PaddingValues,
    viewModel: ExerciseRoutineViewModel,
    navController: NavController,
    sharedViewModel: ExerciseRoutineSharedViewModel,
    routineName: String,
    routineState: Boolean
){
    val routineRecordList by viewModel.allRoutinesRecord.collectAsState(initial = emptyList())
    Scaffold(
        bottomBar = {
            if(routineState){
                BottomBarActualRoutine(routineName, navController, sharedViewModel)
            }
        },
        modifier = Modifier.padding(paddingValues)
    ) {
        InformationScreenContent(
            paddingValues = it,
            routineRecordList = routineRecordList,
            navController = navController,
            viewModel = viewModel,
            sharedViewModel = sharedViewModel
        )
    }
}

@Composable
fun InformationScreenContent(
    paddingValues: PaddingValues,
    routineRecordList: List<RoutineRecord>,
    navController: NavController,
    viewModel: ExerciseRoutineViewModel,
    sharedViewModel: ExerciseRoutineSharedViewModel
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(routineRecordList){item ->
            RoutineRecordPreVisualization(
                item,
                navController = navController,
                viewModel = viewModel,
                sharedViewModel = sharedViewModel
            )
        }
    }
}

@Composable
fun RoutineRecordPreVisualization(
    item: RoutineRecord,
    navController: NavController,
    viewModel: ExerciseRoutineViewModel,
    sharedViewModel: ExerciseRoutineSharedViewModel
){
    Button(
        onClick = {
            sharedViewModel.addTitle(item.routineName)
            viewModel.getRoutineRecordWithExercise(item.routineRecordId)
            navController.navigate(OnWorkOutScreens.PastWorkOutScreen.route)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.width(360.dp),
    ) {
        SecondaryText(text = item.routineName, fontSize = 20)
        Spacer(Modifier.size(30.dp))
        PrimaryText(
            text = item.date,
            fontSize = 16
        )
    }
}