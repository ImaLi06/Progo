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
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

    @Query("SELECT routineRecordId FROM RoutineRecord WHERE routineName = :name LIMIT 1")
    suspend fun getRoutineIdByName(name: String): Long

    @Transaction
    suspend fun insertRoutineRecordWithExercises(
        routineName: String,
        exerciseList: List<Exercise>,
        weightValues: List<List<String>>,
        repsValues: List<List<String>>,
        sets: List<Int>
    ){
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.now().format(formatter)
        val routineRecordAux = RoutineRecord(routineName = routineName, time = 0, date = date)
        upsertRoutineRecord(routineRecordAux)

        val floatWeightValues: List<List<Float>> = weightValues.map { list ->
            list.map { it.toFloat() }
        }
        val intRepsValues: List<List<Int>> = repsValues.map { list ->
            list.map { it.toInt() }
        }

        val exercisesRecordAux: MutableList<ExerciseRecord> = mutableListOf()
        val converter = JsonConverters()
        for(i in exerciseList.indices){
            val exerciseNameAux = exerciseList[i].exerciseName
            val setsAux = sets[i]
            val weightValuesAux = converter.fromFloatListToJson(floatWeightValues[i])
            val repsValuesAux = converter.fromIntListToJson(intRepsValues[i])
            var rmAux = 0.0f
            for(j in floatWeightValues[i].indices){
                val actRm: Float = floatWeightValues[i][j] / (1.0278f - (0.0278f * intRepsValues[i][j]))
                rmAux = maxOf(rmAux, actRm)
            }

            val exerciseRecordAux = ExerciseRecord(
                routineRecordId = getRoutineIdByName(routineName),
                exerciseName = exerciseNameAux,
                rm = rmAux,
                date = date,
                sets = setsAux,
                reps = repsValuesAux,
                weight = weightValuesAux
            )
            exercisesRecordAux.add(exerciseRecordAux)
        }

        for(i in exercisesRecordAux.indices){
            upsertExerciseRecord(exercisesRecordAux[i])
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