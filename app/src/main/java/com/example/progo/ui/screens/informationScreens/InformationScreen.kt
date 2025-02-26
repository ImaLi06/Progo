package com.example.progo.ui.screens.informationScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.progo.data.entities.RoutineRecord
import com.example.progo.ui.component.SecondaryTextTemplate
import com.example.progo.ui.navigationScreens.OnWorkOutScreens
import com.example.progo.ui.viewmodel.ExerciseRoutineSharedViewModel
import com.example.progo.ui.viewmodel.ExerciseRoutineViewModel

@Composable
fun InformationScreen(
    paddingValues: PaddingValues,
    viewModel: ExerciseRoutineViewModel,
    navController: NavController,
    sharedViewModel: ExerciseRoutineSharedViewModel
){
    val routineRecordList by viewModel.allRoutinesRecord.collectAsState(initial = emptyList())
    InformationScreenContent(
        paddingValues = paddingValues,
        routineRecordList = routineRecordList,
        navController = navController,
        viewModel = viewModel,
        sharedViewModel = sharedViewModel
    )
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
            .padding(paddingValues)
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
            sharedViewModel.updateText(item.routineName)
            viewModel.getRoutineRecordWithExercise(item.routineRecordId)
            navController.navigate(OnWorkOutScreens.pastWorkOutScreen.route)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.width(360.dp),
    ) {
        SecondaryTextTemplate(secondaryText = item.routineName, fontSize = 20)
        Spacer(Modifier.width(20.dp))
        Text(
            text = item.date,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.padding(10.dp)
        )
    }
}