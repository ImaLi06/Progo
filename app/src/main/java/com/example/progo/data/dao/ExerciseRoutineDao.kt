package com.example.progo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.progo.data.entities.exercise
import com.example.progo.data.entities.ExerciseRoutine.exerciseRoutineCrossRef
import com.example.progo.data.entities.ExerciseRoutine.ExerciseWithRoutine
import com.example.progo.data.entities.routine
import com.example.progo.data.entities.ExerciseRoutine.routineWithExercise
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

    @Query("DELETE FROM exerciseRoutineCrossRef WHERE routineName = :routineName")
    suspend fun deleteCrossRef(routineName: String)

    suspend fun deleteRoutineWithCrossRef(routine: routine) {
        deleteCrossRef(routine.routineName)
        deleteRoutine(routine)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseRoutineCrossRef(crossRef: exerciseRoutineCrossRef)

    @Query("SELECT * FROM routine ORDER BY routineName ASC")
    fun readAllDataRoutine(): Flow<List<routine>>

    @Query("SELECT * FROM exercise ORDER BY exerciseName ASC")
    fun readAllDataExercise(): Flow<List<exercise>>

    @Transaction
    @Query("SELECT * FROM routine WHERE routineName = :routineName")
    fun getRoutineWithExercise(routineName: String): List<routineWithExercise>

    @Transaction
    @Query("SELECT * FROM exercise WHERE exerciseName = :exerciseName")
    fun getExerciseWithRoutine(exerciseName: String): List<ExerciseWithRoutine>

    @Transaction
    suspend fun insertRoutineWithExercises(routine: routine, exercises: List<exercise>, sets: Int) {
        upsertRoutine(routine)
        exercises.forEach { exercise ->
            upsertExercise(exercise)
            val crossRef = exerciseRoutineCrossRef(exerciseName = exercise.exerciseName, routineName = routine.routineName, sets = sets)
            insertExerciseRoutineCrossRef(crossRef)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDefaultExercises(exercises: List<exercise>)
}