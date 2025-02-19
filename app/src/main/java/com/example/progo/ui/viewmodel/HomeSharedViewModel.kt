package com.example.progo.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.progo.data.entities.Exercise
import com.example.progo.data.entities.ExerciseRoutine.RoutineWithExercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeSharedViewModel(): ViewModel(){
    private val _actualRoutineName = MutableStateFlow("")
    val actualRoutineName = _actualRoutineName.asStateFlow()

    private val _sharedExerciseList = MutableStateFlow<List<Exercise>>(emptyList())
    val sharedExerciseList = _sharedExerciseList.asStateFlow()

    private val _sharedExerciseSetsList = MutableStateFlow<List<Int>>(emptyList())
    val sharedExerciseSetsList = _sharedExerciseSetsList.asStateFlow()

    fun defineRoutineName(name: String){
        _actualRoutineName.value = name
    }

    fun defineExerciseList(auxExerciseList: List<RoutineWithExercise>){
        _sharedExerciseList.value = auxExerciseList[0].gymExercises
    }
}