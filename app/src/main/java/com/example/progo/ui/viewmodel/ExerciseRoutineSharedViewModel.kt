package com.example.progo.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.progo.data.entities.Exercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ExerciseRoutineSharedViewModel(): ViewModel() {
    private val _sharedExerciseList = MutableStateFlow<List<Exercise>>(emptyList())
    val sharedExerciseList = _sharedExerciseList.asStateFlow()

    private val _sharedRoutineName = MutableStateFlow("")
    val sharedRoutineName = _sharedRoutineName.asStateFlow()

    fun addExercise(exercise: Exercise){
        _sharedExerciseList.value += exercise
    }

    fun removeExercise(exercise: Exercise){
        _sharedExerciseList.value -= exercise
    }

    fun updateText(actualValue: String){
        _sharedRoutineName.value = actualValue
    }
}