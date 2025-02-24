package com.example.progo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExerciseRecord(
    @PrimaryKey(autoGenerate = true) val exerciseRecordId: Long? = null,
    val routineRecordId: Long,
    val exerciseName: String,
    val rm: Float,
    val date: String,
    val sets: Int = 1,
    val reps: String,
    val weight: String
)
