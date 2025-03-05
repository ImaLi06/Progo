package com.example.progo.ui.screens.workoutScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.progo.data.entities.Exercise
import com.example.progo.data.entities.Routine
import com.example.progo.ui.component.PrincipalButton
import com.example.progo.ui.component.ProgoTopBar
import com.example.progo.ui.navigationScreens.OnWorkOutScreens
import com.example.progo.ui.viewmodel.ExerciseRoutineSharedViewModel
import com.example.progo.ui.viewmodel.ExerciseRoutineViewModel

@Composable
fun OnWorkOutScreen(
    navController: NavController,
    routineName: String,
    exerciseList: List<Exercise>,
    sharedViewModel: ExerciseRoutineSharedViewModel,
    viewModel: ExerciseRoutineViewModel,
    repsValues: List<List<String>>,
    weightValues: List<List<String>>,
    sets: List<Int>,
    routineState: Boolean,
    workOutScreenType: String
){
    if(!routineState){
        val auxExerciseList by viewModel.actualRoutine.collectAsState()
        val auxExerciseSetsList by viewModel.exerciseList.collectAsState()
        sharedViewModel.defineExerciseList(auxExerciseList)
        sharedViewModel.defineExerciseSetsList(auxExerciseSetsList)
        sharedViewModel.changeRoutineState(true)
    }

    Scaffold(
        topBar = { ProgoTopBar(navController, sharedViewModel, workOutScreenType)}
    ) {
        OnWorkOutScreenContent(
            paddingValues = it,
            routineName = routineName,
            exerciseList = exerciseList,
            sharedViewModel = sharedViewModel,
            sets = sets,
            repsValues = repsValues,
            weightValues = weightValues,
            viewModel = viewModel,
            navController = navController,
            workOutScreenType = workOutScreenType

        )
    }
}

@Composable
fun OnWorkOutScreenContent(
    paddingValues: PaddingValues,
    routineName: String,
    exerciseList: List<Exercise>,
    sharedViewModel: ExerciseRoutineSharedViewModel,
    repsValues: List<List<String>>,
    weightValues: List<List<String>>,
    sets: List<Int>,
    viewModel: ExerciseRoutineViewModel,
    navController: NavController,
    workOutScreenType: String
){
    val auxRepsWeightList by viewModel.lastNRecords.collectAsState()
    viewModel.getLastNRecords(exerciseList, sets)
    val repsList = auxRepsWeightList.first
    val weightsList = auxRepsWeightList.second

    var fixedTitle by remember { mutableStateOf(sharedViewModel.lastTitle) }

    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        contentPadding = PaddingValues(all = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item { Text(text = fixedTitle, fontSize = 30.sp) }
        itemsIndexed(exerciseList){index, item ->
            val repsSubList = repsList.getOrNull(index) ?: emptyList()
            val weightSubList = weightsList.getOrNull(index) ?: emptyList()
            Input(
                item = item,
                index = index,
                sharedViewModel = sharedViewModel,
                repsValues = repsValues,
                weightValues = weightValues,
                repsList = repsSubList,
                weightList = weightSubList,
                exerciseListSize = exerciseList.size,
                navController = navController,
                screenType = workOutScreenType,
                routineName = routineName,
                viewModel = viewModel
            )
        }
        item{
            PrincipalButton(
                text = "Agregar Ejercicio",
                onClick = {
                    navController.navigate(OnWorkOutScreens.OnWorkOutExerciseList.route)
                    sharedViewModel.addTitle("add_exercise")
                },
                height = 60,
                width = 400
            )
        }
        item{
            PrincipalButton(
                text = "Terminar Rutina",
                onClick = {
                    sharedViewModel.deleteLastTitle()
                    saveRoutine(
                        navController = navController,
                        sharedViewModel = sharedViewModel,
                        viewModel = viewModel,
                        routineName = routineName,
                        exerciseList = exerciseList,
                        repsValues = repsValues,
                        weightValues = weightValues,
                        sets = sets,
                        workOutScreenType = workOutScreenType
                    )
                },
                height = 60,
                width = 400
            )
        }
    }
}

fun saveRoutine(
    viewModel: ExerciseRoutineViewModel,
    sharedViewModel: ExerciseRoutineSharedViewModel,
    navController: NavController,
    routineName: String,
    exerciseList: List<Exercise>,
    repsValues: List<List<String>>,
    weightValues: List<List<String>>,
    sets: List<Int>,
    workOutScreenType: String
){
    if(workOutScreenType == "edit"){
        sharedViewModel.changeRoutineState(false)
        println(exerciseList)
        viewModel.insertRoutineWithExercises(
            routine = Routine(routineName),
            exercises = exerciseList,
            sets = sets
        )
        navController.popBackStack()
    } else{
        if(!sharedViewModel.hasEmptySpace()){
            sharedViewModel.changeRoutineState(false)
            viewModel.insertRoutineRecordWithExercisesRecord(routineName, exerciseList, weightValues, repsValues, sets)
            navController.popBackStack()
        }
    }
}