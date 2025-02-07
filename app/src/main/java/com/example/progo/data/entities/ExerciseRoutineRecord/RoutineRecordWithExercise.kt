package com.example.progo.data.entities.ExerciseRoutineRecord

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.progo.data.entities.RoutineRecord
import com.example.progo.data.entities.exercise

data class RoutineRecordWithExercise(
    @Embedded val routineRecord: RoutineRecord,
    @Relation(
        parentColumn = "routineRecordId",
        entityColumn = "exerciseName",
        associateBy = Junction(ExerciseRoutineRecordCrossRef::class)
    )

    val exercises: List<exercise>
)
