package com.example.progo.data.repository

import com.example.progo.data.dao.ExerciseRoutineDao
import com.example.progo.data.entities.Exercise
import com.example.progo.data.entities.ExerciseRoutine.ExerciseWithRoutine
import com.example.progo.data.entities.Routine
import com.example.progo.data.entities.ExerciseRoutine.RoutineWithExercise
import kotlinx.coroutines.flow.Flow

class ExerciseRoutineRepository(private val exerciseRoutineDao: ExerciseRoutineDao) {

    val readAllDataExercise:Flow<List<Exercise>> = exerciseRoutineDao.readAllDataExercise()
    val readAllDataRoutine: Flow<List<Routine>> = exerciseRoutineDao.readAllDataRoutine()

    suspend fun addExercise(exercise: Exercise){
        exerciseRoutineDao.upsertExercise(exercise)
    }
    suspend fun addRoutine(routine: Routine){
        exerciseRoutineDao.upsertRoutine(routine)
    }

    suspend fun deleteExercise(exercise: Exercise) {
        exerciseRoutineDao.deleteExercise(exercise)
    }
    suspend fun deleteRoutine(routine: Routine) {
        exerciseRoutineDao.deleteRoutine(routine)
    }

    suspend fun deleteRoutineWithCrossRef(routine: Routine){
        exerciseRoutineDao.deleteRoutineWithCrossRef(routine)
    }

    fun getRoutineWithExercise(routineName: String): List<RoutineWithExercise>{
        return exerciseRoutineDao.getRoutineWithExercise(routineName)
    }
    fun getExerciseWithRoutine(exerciseName: String): List<ExerciseWithRoutine>{
        return exerciseRoutineDao.getExerciseWithRoutine(exerciseName)
    }

    fun getSetsExerciseRoutine(routineName: String): List<Int>{
        return exerciseRoutineDao.getSetsExerciseRoutine(routineName)
    }

    suspend fun insertRoutineWithExercises(routine: Routine, exercises: List<Exercise>, sets: List<Int>) {
        exerciseRoutineDao.insertRoutineWithExercises(routine, exercises, sets)
    }

    suspend fun addDefaultExercises(exercises: List<Exercise>){
        exerciseRoutineDao.insertDefaultExercises(exercises)
    }
}