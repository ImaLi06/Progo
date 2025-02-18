package com.example.progo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.progo.data.JsonConverters
import com.example.progo.data.entities.Exercise
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
    @Query(
        "SELECT * FROM ExerciseRecord " +
                "WHERE exerciseName = :exerciseName " +
                "ORDER BY exerciseRecordId ASC " +
                "LIMIT :sets"
    )
    fun getLastNExerciseRecords(exerciseName: String, sets: Int): List<ExerciseRecord>

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

    @Transaction
    suspend fun getLastNRecords(exerciseList: List<Exercise>, sets: List<Int>)
    : Pair<List<List<Int>>, List<List<Float>>>{
        val aux = JsonConverters()

        val allReps = mutableListOf<List<Int>>()
        val allWeights = mutableListOf<List<Float>>()

        exerciseList.zip(sets).forEach{(exercise, sets) ->
            val records = getLastNExerciseRecords(exercise.exerciseName, sets)

            val repsList = mutableListOf<Int>()
            val weightList = mutableListOf<Float>()

            for(record in records){
                val repsRecord = aux.fromJsonToIntList(record.reps)
                val weightRecord = aux.fromJsonToFloatList(record.weight)

                repsList.addAll(repsRecord)
                weightList.addAll(weightRecord)
            }
            allReps.add(repsList)
            allWeights.add(weightList)
        }

        return Pair(allReps, allWeights)
    }
}