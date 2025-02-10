package com.example.progo.data.entities.ExerciseRoutineRecord

import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["exerciseName", "routineRecordId"],
    indices = [Index(value = ["exerciseName"]), Index(value = ["routineRecordId"])]
)
data class ExerciseRoutineRecordCrossRef(
    val exerciseName: String,
    val routineRecordId: Int,
    val sets: Int = 1,
    val reps: List<Int> = emptyList()
)
