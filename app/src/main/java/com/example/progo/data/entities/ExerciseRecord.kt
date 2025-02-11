package com.example.progo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExerciseRecord(
    @PrimaryKey(autoGenerate = true) val exerciseRecordId: Int,
    val routineRecordId: Int,
    val exerciseName: String,
    val rm: Int,
    val date: String,
    val sets: Int = 1,
    val reps: String,
    val weight: String
)
