package com.example.progo.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.progo.data.dataBase.ProgoDataBase
import com.example.progo.data.entities.Exercise
import com.example.progo.data.entities.ExerciseRecord
import com.example.progo.data.entities.ExerciseRoutine.ExerciseWithRoutine
import com.example.progo.data.entities.Routine
import com.example.progo.data.entities.ExerciseRoutine.RoutineWithExercise
import com.example.progo.data.entities.ExerciseRoutineRecord.RoutineRecordWithExercise
import com.example.progo.data.entities.RoutineRecord
import com.example.progo.data.repository.ExerciseRoutineRecordRepository
import com.example.progo.data.repository.ExerciseRoutineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExerciseRoutineViewModel(application: Application):AndroidViewModel(application) {

    private val repository: ExerciseRoutineRepository
    private val repositoryRecord: ExerciseRoutineRecordRepository
    val allExercises: Flow<List<Exercise>>
    val allRoutines: Flow<List<Routine>>
    val allRoutinesRecord: Flow<List<RoutineRecord>>

    private val _lastNRecords = MutableStateFlow<Pair<List<List<Int>>, List<List<Float>>>>(Pair(emptyList(), emptyList()))
    val lastNRecords: StateFlow<Pair<List<List<Int>>, List<List<Float>>>> = _lastNRecords

    private val _actualRoutine = MutableStateFlow<List<Exercise>>(emptyList())
    val actualRoutine = _actualRoutine.asStateFlow()

    private val _exerciseSets = MutableStateFlow<List<Int>>(emptyList())
    val exerciseList = _exerciseSets.asStateFlow()

    private val _pastRoutine = MutableStateFlow<List<ExerciseRecord>>(emptyList())
    val pastRoutine = _pastRoutine.asStateFlow()

    private val _exerciseRecords = MutableStateFlow<List<ExerciseRecord>>(emptyList())
    val exerciseRecords = _exerciseRecords.asStateFlow()

    init {
        val exerciseRoutineDao = ProgoDataBase.getProgoDataBase(application).exerciseRoutineDao()
        val exerciseRoutineRecordDao = ProgoDataBase.getProgoDataBase(application).exerciseRoutineRecordDao()
        repository = ExerciseRoutineRepository(exerciseRoutineDao)
        repositoryRecord = ExerciseRoutineRecordRepository(exerciseRoutineRecordDao)

        allExercises = repository.readAllDataExercise
        allRoutines = repository.readAllDataRoutine
        allRoutinesRecord = repositoryRecord.readAllDataRoutineRecord
    }

    private suspend fun insertDefaultExercises() {
        val defaultExercises = listOf(
            Exercise(exerciseName = "Bicep Curl"),
            Exercise(exerciseName = "Bench Press"),
            Exercise(exerciseName = "Incline Bench Press"),
            Exercise(exerciseName = "Lateral Raises"),
            Exercise(exerciseName = "Leg Extension"),
            Exercise(exerciseName = "Pull Over")
        )
        repository.addDefaultExercises(defaultExercises)
    }

    fun addExercise(exercise: Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addExercise(exercise)
        }
    }

    fun getRoutineWithExercise(routineName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getRoutineWithExercise(routineName)
            _actualRoutine.value = result[0].gymExercises
        }
    }

    fun getRoutineRecordWithExercise(routineRecordId: Long?){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repositoryRecord.getRoutineRecordWithExercise(routineRecordId)
            _pastRoutine.value = result
        }
    }

    fun getSetsExerciseRoutine(routineName: String){
        viewModelScope.launch(Dispatchers.IO){
            val result = repository.getSetsExerciseRoutine(routineName)
            _exerciseSets.value = result
        }
    }

    fun getLastNRecords(exerciseList: List<Exercise>, sets: List<Int>){
        viewModelScope.launch(Dispatchers.IO){
            val result = repositoryRecord.getLastNRecords(exerciseList, sets)
            _lastNRecords.value = result
        }
    }

    fun getExerciseRecords(exerciseName: String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repositoryRecord.getExerciseRecords(exerciseName)
            _exerciseRecords.value = result
        }
    }

    fun insertRoutineWithExercises(routine: Routine, exercises: List<Exercise>, sets: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertRoutineWithExercises(routine, exercises, sets)
        }
    }

    fun insertRoutineRecordWithExercisesRecord(
        routineName: String,
        exerciseList: List<Exercise>,
        weightValues: List<List<String>>,
        repsValues: List<List<String>>,
        sets: List<Int>
    ) {
        viewModelScope.launch(Dispatchers.IO){
            repositoryRecord.addRoutineRecordWithExercises(routineName, exerciseList, weightValues, repsValues, sets)
        }
    }

    fun deleteRoutineWithCrossRef(routine: Routine){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteRoutineWithCrossRef(routine)
        }
    }
}