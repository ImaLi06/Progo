package com.example.progo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.progo.data.entities.ExerciseRoutineRecord.ExerciseRoutineRecordCrossRef
import com.example.progo.data.entities.ExerciseRoutineRecord.RoutineRecordWithExercise
import com.example.progo.data.entities.RoutineRecord
import com.example.progo.data.entities.exercise
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseRoutineRecordDao {
    @Upsert
    suspend fun upsertRoutineRecord(routineRecord: RoutineRecord)

    @Upsert
    suspend fun upsertExerciseRecord(exercise: exercise)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseRoutineRecordCrossRef(exerciseRoutineRecordCrossRef: ExerciseRoutineRecordCrossRef)

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
        exercises: List<exercise>,
        sets: Int,
        reps: List<Int>
    ){
        upsertRoutineRecord(routineRecord)
        exercises.forEach {exercise ->
            upsertExerciseRecord(exercise)
            val crossRef = ExerciseRoutineRecordCrossRef(
                exerciseName = exercise.exerciseName,
                routineRecordId =  routineRecord.routineRecordId,
                sets =  sets,
                reps =  reps
                )
            insertExerciseRoutineRecordCrossRef(crossRef)
        }
    }
}