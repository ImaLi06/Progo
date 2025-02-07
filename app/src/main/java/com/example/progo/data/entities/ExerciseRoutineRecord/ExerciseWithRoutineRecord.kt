package com.example.progo.data.entities.ExerciseRoutineRecord

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.progo.data.entities.RoutineRecord
import com.example.progo.data.entities.exercise

data class ExerciseWithRoutineRecord(
    @Embedded val exercise: exercise,
    @Relation(
        parentColumn = "exerciseName",
        entityColumn = "routineRecordId",
        associateBy = Junction(ExerciseRoutineRecordCrossRef::class)
    )
    val routinesRecord: List<RoutineRecord>
)
