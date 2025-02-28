package com.example.progo.ui.screens.workoutScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.progo.data.entities.Exercise
import com.example.progo.data.entities.Routine
import com.example.progo.ui.component.PrincipalTextLabel
import com.example.progo.ui.component.PrincipalButton
import com.example.progo.ui.component.SecondaryTextLabel
import com.example.progo.ui.component.SecondaryTextTemplate
import com.example.progo.ui.component.ProgoTopBar
import com.example.progo.ui.navigationScreens.OnWorkOutScreens
import com.example.progo.ui.navigationScreens.WorkoutScreen
import com.example.progo.ui.viewmodel.ExerciseRoutineSharedViewModel
import com.example.progo.ui.viewmodel.ExerciseRoutineViewModel

@Composable
fun NewWorkoutScreen(
    navController: NavController,
    viewModel: ExerciseRoutineViewModel,
    sharedViewModel: ExerciseRoutineSharedViewModel,
    exerciseList: List<Exercise>,
    routineName: String,
    repsValues: List<List<String>>,
    weightValues: List<List<String>>,
    sets: List<Int>,
    screenType: String
){
    Scaffold(
        topBar = { ProgoTopBar(navController)}
    ) {
        NewWorkoutContent(
            paddingValues = it,
            navController = navController,
            viewModel = viewModel,
            exerciseList = exerciseList,
            routineName = routineName,
            sharedViewModel = sharedViewModel,
            repsValues = repsValues,
            weightValues = weightValues,
            sets = sets,
            screenType = screenType
        )
    }
}

@Composable
fun NewWorkoutContent(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: ExerciseRoutineViewModel,
    exerciseList: List<Exercise>,
    routineName: String,
    sharedViewModel: ExerciseRoutineSharedViewModel,
    repsValues: List<List<String>>,
    weightValues: List<List<String>>,
    sets: List<Int>,
    screenType: String
){
    val auxRepsWeightList by viewModel.lastNRecords.collectAsState()
    viewModel.getLastNRecords(exerciseList, sets)
    val repsList = auxRepsWeightList.first
    val weightsList = auxRepsWeightList.second
    sharedViewModel.changeWorkoutScreenType("create")

    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        contentPadding = PaddingValues(all = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item {
            RoutineNameTextLabel(
                routineName,
                {sharedViewModel.updateText(it)},
                height = 60,
                width = 400
            )
        }
        itemsIndexed(exerciseList) { index, item ->
            val repsSubList = repsList.getOrNull(index) ?: emptyList()
            val weightSubList = weightsList.getOrNull(index) ?: emptyList()
            Input(
                item, index,
                sharedViewModel = sharedViewModel,
                repsValues = repsValues,
                weightValues = weightValues,
                exerciseListSize = exerciseList.size,
                repsList = repsSubList,
                weightList = weightSubList,
                navController = navController,
                screenType = screenType
            )
        }
        item {
            AddExerciseButton(
                text = "Agregar Ejercicio",
                onClick = {navController.navigate(WorkoutScreen.exerciseList.route)},
                height = 60,
                width = 400
            )
        }
        item {
            PrincipalButton(
                text = "Agregar Rutina",
                onClick = {
                    val trimRoutineName = routineName.trim()
                    if(trimRoutineName.isNotEmpty()){
                        viewModel.insertRoutineWithExercises(
                            routine = Routine(routineName = routineName),
                            exercises = exerciseList,
                            sets = sets
                        )
                        navController.popBackStack()
                    }
                },
                height = 60,
                width = 400
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
    weightValues: List<List<String>>,
    exerciseListSize: Int,
    repsList: List<Int>,
    weightList: List<Float>,
    navController: NavController,
    screenType: String
){
    Row(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .width(400.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(40.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            if(index > 0){
                IconButton(
                    onClick = {sharedViewModel.swapExercises(index1 = index, index2 = index - 1)},
                ) { Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Up", tint = MaterialTheme.colorScheme.onSecondary)}
            }
            if(index < exerciseListSize - 1){
                IconButton(
                    onClick = {sharedViewModel.swapExercises(index1 = index, index2 = index + 1)}
                ) { Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Down", tint = MaterialTheme.colorScheme.onSecondary)}
            }
        }
        Column {
            Spacer(modifier = Modifier.size(25.dp, 15.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                //Spacer(modifier = Modifier.size(25.dp, 10.dp))
                ExerciseNameText(
                    navController = navController,
                    exerciseName = item.exerciseName,
                    fontSize = 20,
                    sharedViewModel = sharedViewModel,
                    screenType = screenType,
                    viewModel = viewModel()
                )
                InputAdditionalOptions(
                    index = index,
                    sharedViewModel = sharedViewModel
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Spacer(modifier = Modifier.size(10.dp))
                LastRecord(
                    sets = sharedViewModel.getSets(index),
                    repsList = repsList,
                    weightList = weightList
                )
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
}

@Composable
fun LastRecord(sets: Int?, repsList: List<Int>, weightList: List<Float>){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Record")
        Spacer(modifier = Modifier.size(10.dp))
        if(sets != null){
            repeat(sets){subIndex ->
                val weight = weightList.getOrNull(subIndex) ?: 0.0
                val reps = repsList.getOrNull(subIndex) ?: 0
                Spacer(modifier = Modifier.size(10.dp, 10.dp))
                Text("${weight}x${reps}")
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
fun InputAdditionalOptions(
    index: Int,
    sharedViewModel: ExerciseRoutineSharedViewModel,
){
    var expanded by remember { mutableStateOf(false)}
    Box{
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = "Más opciones", tint = MaterialTheme.colorScheme.onSecondary)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = {expanded = false}) {
            DropdownMenuItem(
                onClick = {
                    sharedViewModel.addSet(index)
                },
                text = {Text("Agregar set")}
            )
            val setNum = sharedViewModel.getSets(index)
            if(setNum != null && setNum > 1){
                DropdownMenuItem(onClick = {sharedViewModel.deleteSet(index)}, text = { Text("Eliminar set")})
            }
            DropdownMenuItem(onClick = {sharedViewModel.removeExercise(index)}, text = { Text("Eliminar ejercicio")})
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

@Composable
fun ExerciseNameText(
    navController: NavController,
    exerciseName: String,
    fontSize: Int,
    sharedViewModel: ExerciseRoutineSharedViewModel,
    viewModel: ExerciseRoutineViewModel,
    screenType: String
){
    Text(
        text = exerciseName,
        modifier = Modifier.clickable {
            sharedViewModel.updateText(exerciseName)
            if(screenType == "create"){
                navController.navigate(WorkoutScreen.exerciseStats.route)
            }
            else{
                navController.navigate(OnWorkOutScreens.onWorkOutExerciseRecords.route)
            }
        },
        color = Color.Green,
        fontSize = fontSize.sp
    )
}