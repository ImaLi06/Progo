package com.example.progo.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.progo.data.entities.Exercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ExerciseRoutineSharedViewModel(): ViewModel() {
    private val _sharedExerciseList = MutableStateFlow<List<Exercise>>(emptyList())
    val sharedExerciseList = _sharedExerciseList.asStateFlow()

    private val _sharedExerciseSetsList = MutableStateFlow<List<Int>>(emptyList())
    val sharedExerciseSetsList = _sharedExerciseSetsList.asStateFlow()

    private val _sharedRepsValue = MutableStateFlow<List<List<String>>>(emptyList())
    val sharedRepsValue = _sharedRepsValue.asStateFlow()

    private val _sharedWeightValue = MutableStateFlow<List<List<String>>>(emptyList())
    val sharedWeightValue = _sharedWeightValue.asStateFlow()

    private val _sharedRoutineName = MutableStateFlow("")
    val sharedRoutineName = _sharedRoutineName.asStateFlow()

    fun addExercise(exercise: Exercise){
        _sharedExerciseList.value += exercise
        _sharedExerciseSetsList.value += 1
        _sharedRepsValue.value += listOf(listOf(""))
        _sharedWeightValue.value += listOf(listOf(""))
    }

    fun removeExercise(exercise: Exercise){
        _sharedExerciseList.value -= exercise
    }

    fun updateText(actualValue: String){
        _sharedRoutineName.value = actualValue
    }

    fun addSet(index: Int){
        _sharedExerciseSetsList.value = _sharedExerciseSetsList.value.mapIndexed{ i, sets ->
            if(i == index) sets + 1 else sets
        }
        _sharedRepsValue.value = _sharedRepsValue.value.mapIndexed{ i, sublist ->
            if(i == index) sublist + "" else sublist
        }
        _sharedWeightValue.value = _sharedWeightValue.value.mapIndexed{ i, subList ->
            if(i == index) subList + "" else subList
        }
    }

    fun getSets(index: Int): Int?{
        return _sharedExerciseSetsList.value.getOrNull(index)
    }

    fun updateRepsLabelValue(sectionIndex: Int, index: Int, newText: String){
        _sharedRepsValue.update { currentList ->
            currentList.mapIndexed { sIndex, section ->
                if (sIndex == sectionIndex) {
                    val updatedSection = section.mapIndexed { iIndex, value ->
                        if (iIndex == index) newText else value
                    }
                    updatedSection
                } else {
                    section
                }
            }
        }
    }

    fun updateWeightLabelValue(sectionIndex: Int, index: Int, newText: String){
        _sharedWeightValue.update { currentList ->
            currentList.mapIndexed{ sIndex, section ->
                if(sIndex == sectionIndex){
                    val updatedSection = section.mapIndexed{ iIndex, value ->
                        if(iIndex == index) newText else value
                    }
                    updatedSection
                } else {
                    section
                }
            }
        }
    }
}