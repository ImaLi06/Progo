package com.example.progo.data.repository

import com.example.progo.data.dao.ExerciseRoutineDao
import com.example.progo.data.entities.exercise
import com.example.progo.data.entities.ExerciseRoutine.ExerciseWithRoutine
import com.example.progo.data.entities.routine
import com.example.progo.data.entities.ExerciseRoutine.routineWithExercise
import kotlinx.coroutines.flow.Flow

class exerciseRoutineRepository(private val exerciseRoutineDao: ExerciseRoutineDao) {

    val readAllDataExercise:Flow<List<exercise>> = exerciseRoutineDao.readAllDataExercise()
    val readAllDataRoutine: Flow<List<routine>> = exerciseRoutineDao.readAllDataRoutine()

    suspend fun addExercise(exercise: exercise){
        exerciseRoutineDao.upsertExercise(exercise)
    }
    suspend fun addRoutine(routine: routine){
        exerciseRoutineDao.upsertRoutine(routine)
    }

    suspend fun deleteExercise(exercise: exercise) {
        exerciseRoutineDao.deleteExercise(exercise)
    }
    suspend fun deleteRoutine(routine: routine) {
        exerciseRoutineDao.deleteRoutine(routine)
    }

    suspend fun deleteRoutineWithCrossRef(routine: routine){
        exerciseRoutineDao.deleteRoutineWithCrossRef(routine)
    }

    fun getRoutineWithExercise(routineName: String): List<routineWithExercise>{
        return exerciseRoutineDao.getRoutineWithExercise(routineName)
    }
    fun getExerciseWithRoutine(exerciseName: String): List<ExerciseWithRoutine>{
        return exerciseRoutineDao.getExerciseWithRoutine(exerciseName)
    }

    suspend fun insertRoutineWithExercises(routine: routine, exercises: List<exercise>, sets: Int) {
        exerciseRoutineDao.insertRoutineWithExercises(routine, exercises, sets)
    }

    suspend fun AddDefaultExercises(exercises: List<exercise>){
        exerciseRoutineDao.insertDefaultExercises(exercises)
    }
}