package com.example.progo.data.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ExerciseWithRoutine(
    @Embedded val exercise: exercise,
    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "routineId",
        associateBy = Junction(exerciseRoutineCrossRef::class)
    )
    val workoutRoutines: List<routine>
)
