package com.example.progo.data.repository

import com.example.progo.data.dao.ExerciseRoutineRecordDao
import com.example.progo.data.entities.ExerciseRoutineRecord.RoutineRecordWithExercise
import com.example.progo.data.entities.RoutineRecord
import com.example.progo.data.entities.Exercise
import com.example.progo.data.entities.ExerciseRecord
import kotlinx.coroutines.flow.Flow

class ExerciseRoutineRecordRepository(private val exerciseRoutineRecordDao: ExerciseRoutineRecordDao) {
    val readAllDataRoutineRecord: Flow<List<RoutineRecord>> = exerciseRoutineRecordDao.readAllDataRoutineRecord()

    suspend fun addRoutineRecord(routineRecord: RoutineRecord){
        exerciseRoutineRecordDao.upsertRoutineRecord(routineRecord)
    }

    suspend fun deleteRoutineRecord(routineRecord: RoutineRecord){
        exerciseRoutineRecordDao.deleteRoutineRecord(routineRecord)
    }

    suspend fun addRoutineRecordWithExercises(
        routineName: String,
        exerciseList: List<Exercise>,
        weightValues: List<List<String>>,
        repsValues: List<List<String>>,
        sets: List<Int>
    ){
        exerciseRoutineRecordDao.insertRoutineRecordWithExercises(routineName, exerciseList, weightValues, repsValues, sets)
    }

    suspend fun getLastNRecords(exerciseList: List<Exercise>, sets: List<Int>)
    :  Pair<List<List<Int>>, List<List<Float>>>{
        return exerciseRoutineRecordDao.getLastNRecords(exerciseList, sets)
    }

    fun getRoutineRecordWithExercise(routineRecordId: Int): List<RoutineRecordWithExercise>{
        return exerciseRoutineRecordDao.getRoutineRecordWithExercise(routineRecordId)
    }
}