package com.example.progo.data.entities.ExerciseRoutine

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.progo.data.entities.Exercise
import com.example.progo.data.entities.Routine

data class ExerciseWithRoutine(
    @Embedded val exercise: Exercise,
    @Relation(
        parentColumn = "exerciseName",
        entityColumn = "routineName",
        associateBy = Junction(ExerciseRoutineCrossRef::class)
    )
    val workoutRoutines: List<Routine>
)
