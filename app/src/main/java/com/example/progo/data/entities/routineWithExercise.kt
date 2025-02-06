package com.example.progo.data.entities

import androidx.lifecycle.LiveData
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import kotlinx.coroutines.flow.Flow

data class routineWithExercise(
    @Embedded val routine: routine,
    @Relation(
        parentColumn = "routineId",
        entityColumn = "exerciseId",
        associateBy = Junction(exerciseRoutineCrossRef::class)
    )

    val gymExercises: List<exercise>
)
