package com.example.progo.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.progo.data.entities.exercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ExerciseRoutineSharedViewModel(): ViewModel() {
    private val _sharedExerciseList = MutableStateFlow<List<exercise>>(emptyList())
    val sharedExerciseList = _sharedExerciseList.asStateFlow()

    private val _sharedRoutineName = MutableStateFlow("")
    val sharedRoutineName = _sharedRoutineName.asStateFlow()

    fun addExercise(exercise: exercise){
        _sharedExerciseList.value += exercise
    }

    fun removeExercise(exercise: exercise){
        _sharedExerciseList.value -= exercise
    }

    fun updateText(actualValue: String){
        _sharedRoutineName.value = actualValue
    }
}