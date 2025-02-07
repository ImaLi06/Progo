package com.example.progo.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.progo.data.dataBase.progoDataBase
import com.example.progo.data.entities.exercise
import com.example.progo.data.entities.ExerciseRoutine.ExerciseWithRoutine
import com.example.progo.data.entities.routine
import com.example.progo.data.entities.ExerciseRoutine.routineWithExercise
import com.example.progo.data.repository.exerciseRoutineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ExerciseRoutineViewModel(application: Application):AndroidViewModel(application) {

    private val repository: exerciseRoutineRepository
    val allExercises: Flow<List<exercise>>
    val allRoutines: Flow<List<routine>>

    init {
        val exerciseRoutineDao = progoDataBase.getProgoDataBase(application).exerciseRoutineDao()
        repository = exerciseRoutineRepository(exerciseRoutineDao)

        allExercises = repository.readAllDataExercise
        allRoutines = repository.readAllDataRoutine
    }

    private suspend fun insertDefaultExercises() {
        val defaultExercises = listOf(
            exercise(exerciseName = "Bicep Curl"),
            exercise(exerciseName = "Bench Press"),
            exercise(exerciseName = "Incline Bench Press"),
            exercise(exerciseName = "Lateral Raises"),
            exercise(exerciseName = "Leg Extension"),
            exercise(exerciseName = "Pull Over")
        )
        repository.AddDefaultExercises(defaultExercises)
    }

    fun AddExercise(exercise: exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addExercise(exercise)
        }
    }

    fun AddRoutine(routine: routine) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRoutine(routine)
        }
    }

    fun GetRoutineWithExercise(routineName: String): List<routineWithExercise> {
        return repository.getRoutineWithExercise(routineName)
    }

    fun GetExerciseWithRoutine(exerciseName: String): List<ExerciseWithRoutine>{
        return repository.getExerciseWithRoutine(exerciseName)
    }

    fun InsertRoutineWithExercises(routine: routine, exercises: List<exercise>, sets: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertRoutineWithExercises(routine, exercises, sets)
        }
    }

    fun DeleteRoutineWithCrossRef(routine: routine){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteRoutineWithCrossRef(routine)
        }
    }
}