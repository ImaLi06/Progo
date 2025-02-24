package com.example.progo.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.progo.data.entities.Exercise
import com.example.progo.data.entities.ExerciseRoutine.RoutineWithExercise
import com.example.progo.ui.component.dragDropList.move
import com.example.progo.ui.viewmodel.extra.Quadruple
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

    private val _routineStarted = MutableStateFlow(false)
    val routineStarted = _routineStarted.asStateFlow()

    fun addExercise(exercise: Exercise){
        _sharedExerciseList.value += exercise
        _sharedExerciseSetsList.value += 1
        _sharedRepsValue.value += listOf(listOf(""))
        _sharedWeightValue.value += listOf(listOf(""))
    }

    fun removeExercise(index: Int){
        _sharedExerciseList.value = _sharedExerciseList.value.toMutableList().apply {
            if (index in indices) removeAt(index)
        }
        _sharedWeightValue.value = _sharedWeightValue.value.toMutableList().apply {
            if(index in indices) removeAt(index)
        }
        _sharedRepsValue.value = _sharedRepsValue.value.toMutableList().apply {
            if(index in indices) removeAt(index)
        }
        _sharedExerciseSetsList.value = _sharedExerciseSetsList.value.toMutableList().apply {
            if(index in indices) removeAt(index)
        }

        println(_sharedWeightValue.value)
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

    fun deleteSet(index: Int){
        _sharedExerciseSetsList.value = _sharedExerciseSetsList.value.mapIndexed{ i, sets ->
            if(i == index) sets - 1 else sets
        }
        _sharedWeightValue.value = _sharedWeightValue.value.mapIndexed { i, sublist ->
            if (i == index && sublist.isNotEmpty()) sublist.dropLast(1) else sublist
        }
        _sharedRepsValue.value = _sharedRepsValue.value.mapIndexed { i, sublist ->
            if (i == index && sublist.isNotEmpty()) sublist.dropLast(1) else sublist
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

    fun swapExercises(index1: Int, index2: Int) {
        val combinedList = _sharedExerciseList.value
            .zip(_sharedExerciseSetsList.value)
            .zip(_sharedRepsValue.value) { (exercise, sets), reps ->
                Triple(exercise, sets, reps)
            }
            .zip(_sharedWeightValue.value) { (exercise, sets, reps), weights ->
                Quadruple(exercise, sets, reps, weights)
            }

        println(combinedList)

        val updatedCombinedList = combinedList.toMutableList()
        if (index1 in updatedCombinedList.indices && index2 in updatedCombinedList.indices) {
            updatedCombinedList[index1] = updatedCombinedList[index2].also {
                updatedCombinedList[index2] = updatedCombinedList[index1]
            }
        }


        val updatedExerciseList = updatedCombinedList.map { it.first }
        val updatedSetsList = updatedCombinedList.map { it.second }
        val updatedRepsList = updatedCombinedList.map { it.third }
        val updatedWeightList = updatedCombinedList.map { it.fourth }

        _sharedExerciseList.value = updatedExerciseList
        _sharedExerciseSetsList.value = updatedSetsList
        _sharedRepsValue.value = updatedRepsList
        _sharedWeightValue.value = updatedWeightList
    }

    fun defineExerciseList(auxExerciseList: List<Exercise>){
        _sharedExerciseList.value = auxExerciseList
    }

    fun defineExerciseSetsList(sets: List<Int>){
        _sharedExerciseSetsList.value = sets

        _sharedRepsValue.value = sets.map { List(it) {""} }
        _sharedWeightValue.value = sets.map { List(it) {""} }
    }

    fun changeRoutineState(state: Boolean){
        _routineStarted.value = state
    }

    fun hasEmptySpace(): Boolean {
        return _sharedRepsValue.value.any { list -> list.any { it.isBlank() } } ||
                _sharedWeightValue.value.any { list -> list.any { it.isBlank() } }
    }
}