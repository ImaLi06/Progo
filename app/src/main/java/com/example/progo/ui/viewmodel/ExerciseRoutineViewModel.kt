package com.example.progo.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.progo.data.dataBase.progoDataBase
import com.example.progo.data.entities.exercise
import com.example.progo.data.entities.ExerciseRoutine.ExerciseWithRoutine
import com.example.progo.data.entities.routine
import com.example.progo.data.entities.ExerciseRoutine.routineWithExercise
import com.example.progo.data.entities.ExerciseRoutineRecord.RoutineRecordWithExercise
import com.example.progo.data.entities.RoutineRecord
import com.example.progo.data.repository.ExerciseRoutineRecordRepository
import com.example.progo.data.repository.exerciseRoutineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ExerciseRoutineViewModel(application: Application):AndroidViewModel(application) {

    private val repository: exerciseRoutineRepository
    private val repositoryRecord: ExerciseRoutineRecordRepository
    val allExercises: Flow<List<exercise>>
    val allRoutines: Flow<List<routine>>
    val allRoutinesRecord: Flow<List<RoutineRecord>>

    init {
        val exerciseRoutineDao = progoDataBase.getProgoDataBase(application).exerciseRoutineDao()
        val exerciseRoutineRecordDao = progoDataBase.getProgoDataBase(application).exerciseRoutineRecordDao()
        repository = exerciseRoutineRepository(exerciseRoutineDao)
        repositoryRecord = ExerciseRoutineRecordRepository(exerciseRoutineRecordDao)

        allExercises = repository.readAllDataExercise
        allRoutines = repository.readAllDataRoutine
        allRoutinesRecord = repositoryRecord.readAllDataRoutineRecord
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

    fun addExercise(exercise: exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addExercise(exercise)
        }
    }

    fun getRoutineWithExercise(routineName: String): List<routineWithExercise> {
        return repository.getRoutineWithExercise(routineName)
    }

    fun getExerciseWithRoutine(exerciseName: String): List<ExerciseWithRoutine>{
        return repository.getExerciseWithRoutine(exerciseName)
    }

    fun getRoutineRecordWithExercise(routineRecordId: Int): List<RoutineRecordWithExercise>{
        return repositoryRecord.getRoutineRecordWithExercise(routineRecordId)
    }

    fun insertRoutineWithExercises(routine: routine, exercises: List<exercise>, sets: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertRoutineWithExercises(routine, exercises, sets)
        }
    }

    fun insertRoutineRecordWithExercises(routineRecord: RoutineRecord, exercises: List<exercise>, sets: Int, reps: List<Int>){
        viewModelScope.launch(Dispatchers.IO){
            repositoryRecord.addRoutineRecordWithExercises(routineRecord, exercises, sets, reps)
        }
    }

    fun deleteRoutineWithCrossRef(routine: routine){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteRoutineWithCrossRef(routine)
        }
    }
}