package com.example.progo.data.entities.ExerciseRoutine

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.progo.data.entities.exercise
import com.example.progo.data.entities.routine

data class routineWithExercise(
    @Embedded val routine: routine,
    @Relation(
        parentColumn = "routineName",
        entityColumn = "exerciseName",
        associateBy = Junction(exerciseRoutineCrossRef::class)
    )

    val gymExercises: List<exercise>
)
