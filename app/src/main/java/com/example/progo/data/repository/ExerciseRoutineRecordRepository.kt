package com.example.progo.data.repository

import com.example.progo.data.dao.ExerciseRoutineRecordDao
import com.example.progo.data.entities.ExerciseRoutine.routineWithExercise
import com.example.progo.data.entities.ExerciseRoutineRecord.RoutineRecordWithExercise
import com.example.progo.data.entities.RoutineRecord
import com.example.progo.data.entities.exercise
import kotlinx.coroutines.flow.Flow

class ExerciseRoutineRecordRepository(private val exerciseRoutineRecordDao: ExerciseRoutineRecordDao) {
    val readAllDataRoutineRecord: Flow<List<RoutineRecord>> = exerciseRoutineRecordDao.readAllDataRoutineRecord()

    suspend fun addRoutineRecord(routineRecord: RoutineRecord){
        exerciseRoutineRecordDao.upsertRoutineRecord(routineRecord)
    }

    suspend fun deleteRoutineRecord(routineRecord: RoutineRecord){
        exerciseRoutineRecordDao.deleteRoutineRecord(routineRecord)
    }

    suspend fun addRoutineRecordWithExercises(routineRecord: RoutineRecord, exercises: List<exercise>, sets: Int){
        exerciseRoutineRecordDao.insertRoutineRecordWithExercises(routineRecord, exercises, sets)
    }

    fun getRoutineRecordWithExercise(routineRecordId: Int): List<RoutineRecordWithExercise>{
        return exerciseRoutineRecordDao.getRoutineRecordWithExercise(routineRecordId)
    }
}