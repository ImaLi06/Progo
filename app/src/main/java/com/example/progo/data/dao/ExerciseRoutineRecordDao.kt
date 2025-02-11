package com.example.progo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.progo.data.entities.ExerciseRoutineRecord.RoutineRecordWithExercise
import com.example.progo.data.entities.RoutineRecord
import com.example.progo.data.entities.ExerciseRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseRoutineRecordDao {
    @Upsert
    suspend fun upsertRoutineRecord(routineRecord: RoutineRecord)

    @Upsert
    suspend fun upsertExerciseRecord(exerciseRecord: ExerciseRecord)

    @Delete
    suspend fun deleteRoutineRecord(routineRecord: RoutineRecord)

    @Transaction
    @Query("SELECT * FROM RoutineRecord ORDER BY routineRecordId DESC")
    fun readAllDataRoutineRecord(): Flow<List<RoutineRecord>>

    @Transaction
    @Query("SELECT * FROM RoutineRecord WHERE routineRecordId= :routineRecordId")
    fun getRoutineRecordWithExercise(routineRecordId: Int): List<RoutineRecordWithExercise>

    @Transaction
    suspend fun insertRoutineRecordWithExercises(
        routineRecord: RoutineRecord,
        exercisesRecord: List<ExerciseRecord>,
    ){
        upsertRoutineRecord(routineRecord)
        exercisesRecord.forEach { exerciseRecord ->
            upsertExerciseRecord(exerciseRecord.copy(routineRecordId = routineRecord.routineRecordId))
        }
    }
}