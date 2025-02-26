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
import com.example.progo.data.entities.RoutineRecord
import com.example.progo.ui.component.SecondaryTextTemplate
import com.example.progo.ui.viewmodel.ExerciseRoutineViewModel

@Composable
fun InformationScreen(
    paddingValues: PaddingValues,
    viewModel: ExerciseRoutineViewModel
){
    val routineRecordList by viewModel.allRoutinesRecord.collectAsState(initial = emptyList())
    InformationScreenContent(
        paddingValues = paddingValues,
        routineRecordList = routineRecordList
    )
}

@Composable
fun InformationScreenContent(
    paddingValues: PaddingValues,
    routineRecordList: List<RoutineRecord>
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
            RoutineRecordPreVisualization(item)
        }
    }
}

@Composable
fun RoutineRecordPreVisualization(item: RoutineRecord){
    Button(
        onClick = {},
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