package com.example.progo.data.entities.ExerciseRoutine

import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["exerciseName", "routineName"],
    indices = [Index(value = ["exerciseName"]), Index(value = ["routineName"])]
)
data class exerciseRoutineCrossRef(
    val exerciseName: String,
    val routineName: String,
    val sets: Int
)
