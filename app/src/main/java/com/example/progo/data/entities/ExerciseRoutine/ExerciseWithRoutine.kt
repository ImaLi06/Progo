package com.example.progo.data.entities.ExerciseRoutine

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.progo.data.entities.exercise
import com.example.progo.data.entities.routine

data class ExerciseWithRoutine(
    @Embedded val exercise: exercise,
    @Relation(
        parentColumn = "exerciseName",
        entityColumn = "routineName",
        associateBy = Junction(exerciseRoutineCrossRef::class)
    )
    val workoutRoutines: List<routine>
)
