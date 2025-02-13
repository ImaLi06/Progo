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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.progo.data.entities.Exercise
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
    sharedViewModel: ExerciseRoutineSharedViewModel,
    exerciseList: List<Exercise>,
    routineName: String,
    setsList: List<Int>,
    repsValues: List<List<String>>,
    weightValues: List<List<String>>
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
            sharedViewModel = sharedViewModel,
            setsList = setsList,
            repsValues = repsValues,
            weightValues = weightValues
        )
    }
}

@Composable
fun MainWorkoutContent(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: ExerciseRoutineViewModel,
    exerciseList: List<Exercise>,
    routineName: String,
    sharedViewModel: ExerciseRoutineSharedViewModel,
    setsList: List<Int>,
    repsValues: List<List<String>>,
    weightValues: List<List<String>>
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
        itemsIndexed(exerciseList) { index, item ->
            Input(
                item, index,
                sharedViewModel = sharedViewModel,
                repsValues = repsValues,
                weightValues = weightValues
            )
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
                onClick = {},
                height = 60,
                width = 350
            )
        }
    }
}

@Composable
fun Input(
    item: Exercise,
    index: Int,
    sharedViewModel: ExerciseRoutineSharedViewModel,
    repsValues: List<List<String>>,
    weightValues: List<List<String>>
){
    var aux: String = ""
    Column(modifier = Modifier
        .clip(shape = RoundedCornerShape(15.dp))
        .background(MaterialTheme.colorScheme.secondary)
        .width(350.dp),
    ) {
        Spacer(modifier = Modifier.size(25.dp, 10.dp))
        Row{
            Spacer(modifier = Modifier.size(25.dp, 10.dp))
            SecondaryTextTemplate(item.exerciseName, 20)
            IconButton(
                onClick = {sharedViewModel.addSet(index)}
            ) { Icon(Icons.Default.Add, contentDescription = "Add set")}
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Spacer(modifier = Modifier.size(10.dp))
            LastRecord(sets = sharedViewModel.getSets(index))
            Weight(
                sets = sharedViewModel.getSets(index),
                sharedViewModel = sharedViewModel,
                sectionIndex = index,
                weightValues = weightValues
            )
            Reps(
                sets = sharedViewModel.getSets(index),
                sectionIndex = index,
                sharedViewModel = sharedViewModel,
                repsValues = repsValues
            )
        }
        Spacer(modifier = Modifier.size(25.dp, 10.dp))
    }
}

@Composable
fun LastRecord(sets: Int?){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Record")
        Spacer(modifier = Modifier.size(10.dp))
        if(sets != null){
            repeat(sets){
                Spacer(modifier = Modifier.size(10.dp, 10.dp))
                Text("0x0")
                Spacer(modifier = Modifier.size(10.dp, 36.dp))
            }
        }
    }
}

@Composable
fun Weight(
    sets: Int?,
    sharedViewModel: ExerciseRoutineSharedViewModel,
    sectionIndex: Int,
    weightValues: List<List<String>>
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Peso")
        Spacer(modifier = Modifier.size(10.dp))
        if(sets != null){
            repeat(sets){index ->
                SecondaryTextLabel(
                    value = weightValues.getOrNull(sectionIndex)?.getOrNull(index) ?: "",
                    onValueChange = { newValue ->
                        sharedViewModel.updateWeightLabelValue(sectionIndex, index, newValue)
                    },
                    50,
                    100
                )
                Spacer(modifier = Modifier.size(10.dp, 20.dp))
            }
        }
    }
}

@Composable
fun Reps(
    sets: Int?,
    sharedViewModel: ExerciseRoutineSharedViewModel,
    sectionIndex: Int,
    repsValues: List<List<String>>
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Repeticiones")
        Spacer(modifier = Modifier.size(10.dp))
        if(sets != null){
            repeat(sets){index ->
                SecondaryTextLabel(
                    value = repsValues.getOrNull(sectionIndex)?.getOrNull(index) ?: "",
                    onValueChange = {newValue ->
                        sharedViewModel.updateRepsLabelValue(sectionIndex, index, newValue)
                    },
                    50,
                    100
                )
                Spacer(modifier = Modifier.size(10.dp, 20.dp))
            }
        }
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