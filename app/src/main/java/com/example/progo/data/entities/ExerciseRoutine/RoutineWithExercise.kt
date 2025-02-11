package com.example.progo.data.entities.ExerciseRoutine

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.progo.data.entities.Exercise
import com.example.progo.data.entities.Routine

data class RoutineWithExercise(
    @Embedded val routine: Routine,
    @Relation(
        parentColumn = "routineName",
        entityColumn = "exerciseName",
        associateBy = Junction(ExerciseRoutineCrossRef::class)
    )

    val gymExercises: List<Exercise>
)
