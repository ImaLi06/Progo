package com.example.progo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.progo.data.entities.Exercise
import com.example.progo.data.entities.ExerciseRoutine.ExerciseRoutineCrossRef
import com.example.progo.data.entities.ExerciseRoutine.ExerciseWithRoutine
import com.example.progo.data.entities.Routine
import com.example.progo.data.entities.ExerciseRoutine.RoutineWithExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseRoutineDao {

    @Upsert
    suspend fun upsertExercise(exercise: Exercise)

    @Upsert
    suspend fun upsertRoutine(routine: Routine)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    @Delete
    suspend fun deleteRoutine(routine: Routine)

    @Query("DELETE FROM ExerciseRoutineCrossRef WHERE routineName = :routineName")
    suspend fun deleteCrossRef(routineName: String)

    suspend fun deleteRoutineWithCrossRef(routine: Routine) {
        deleteCrossRef(routine.routineName)
        deleteRoutine(routine)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseRoutineCrossRef(crossRef: ExerciseRoutineCrossRef)

    @Query("SELECT * FROM Routine ORDER BY routineName ASC")
    fun readAllDataRoutine(): Flow<List<Routine>>

    @Query("SELECT * FROM Exercise ORDER BY exerciseName ASC")
    fun readAllDataExercise(): Flow<List<Exercise>>

    @Transaction
    @Query("SELECT * FROM Routine WHERE routineName = :routineName")
    suspend fun getRoutineWithExercise(routineName: String): List<RoutineWithExercise>

    @Transaction
    @Query("SELECT * FROM Exercise WHERE exerciseName = :exerciseName")
    fun getExerciseWithRoutine(exerciseName: String): List<ExerciseWithRoutine>

    @Transaction
    @Query("SELECT sets FROM ExerciseRoutineCrossRef WHERE routineName = :routineName")
    fun getSetsExerciseRoutine(routineName: String): List<Int>

    @Transaction
    suspend fun insertRoutineWithExercises(routine: Routine, exercises: List<Exercise>, sets: List<Int>) {
        upsertRoutine(routine)
        exercises.forEachIndexed() { index, exercise ->
            upsertExercise(exercise)
            val crossRef = ExerciseRoutineCrossRef(
                exerciseName = exercise.exerciseName,
                routineName = routine.routineName,
                sets = sets[index]
            )
            insertExerciseRoutineCrossRef(crossRef)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDefaultExercises(exercises: List<Exercise>)
}