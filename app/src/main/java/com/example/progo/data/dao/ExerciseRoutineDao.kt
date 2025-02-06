package com.example.progo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.progo.data.entities.exercise
import com.example.progo.data.entities.exerciseRoutineCrossRef
import com.example.progo.data.entities.ExerciseWithRoutine
import com.example.progo.data.entities.routine
import com.example.progo.data.entities.routineWithExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseRoutineDao {

    @Upsert
    suspend fun upsertExercise(exercise: exercise)

    @Upsert
    suspend fun upsertRoutine(routine: routine)

    @Delete
    suspend fun deleteExercise(exercise: exercise)

    @Delete
    suspend fun deleteRoutine(routine: routine)

    @Query("DELETE FROM exerciseRoutineCrossRef WHERE routineId = :routineId")
    suspend fun deleteCrossRef(routineId: Int)

    suspend fun deleteRoutineWithCrossRef(routine: routine) {
        deleteCrossRef(routine.routineId)
        deleteRoutine(routine)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseRoutineCrossRef(crossRef: exerciseRoutineCrossRef)

    @Query("SELECT * FROM routine ORDER BY routineId ASC")
    fun readAllDataRoutine(): Flow<List<routine>>

    @Query("SELECT * FROM exercise ORDER BY exerciseId ASC")
    fun readAllDataExercise(): Flow<List<exercise>>

    @Transaction
    @Query("SELECT * FROM routine WHERE routineId = :routineId")
    fun getRoutineWithExercise(routineId: Int): List<routineWithExercise>

    @Transaction
    @Query("SELECT * FROM exercise WHERE exerciseId = :exerciseId")
    fun getExerciseWithRoutine(exerciseId: Int): List<ExerciseWithRoutine>

    @Transaction
    suspend fun insertRoutineWithExercises(routine: routine, exercises: List<exercise>) {
        upsertRoutine(routine)
        exercises.forEach { exercise ->
            upsertExercise(exercise)
            val crossRef = exerciseRoutineCrossRef(exerciseId = exercise.exerciseId, routineId = routine.routineId)
            insertExerciseRoutineCrossRef(crossRef)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDefaultExercises(exercises: List<exercise>)
}