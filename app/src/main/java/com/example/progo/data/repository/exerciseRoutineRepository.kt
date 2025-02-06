package com.example.progo.data.repository

import com.example.progo.data.dao.ExerciseRoutineDao
import com.example.progo.data.entities.exercise
import com.example.progo.data.entities.ExerciseWithRoutine
import com.example.progo.data.entities.routine
import com.example.progo.data.entities.routineWithExercise
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

    fun getRoutineWithExercise(routineId: Int): List<routineWithExercise>{
        return exerciseRoutineDao.getRoutineWithExercise(routineId)
    }
    fun getExerciseWithRoutine(exerciseId: Int): List<ExerciseWithRoutine>{
        return exerciseRoutineDao.getExerciseWithRoutine(exerciseId)
    }

    suspend fun insertRoutineWithExercises(routine: routine, exercises: List<exercise>) {
        exerciseRoutineDao.insertRoutineWithExercises(routine, exercises)
    }

    suspend fun AddDefaultExercises(exercises: List<exercise>){
        exerciseRoutineDao.insertDefaultExercises(exercises)
    }
}