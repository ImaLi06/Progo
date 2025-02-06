package com.example.progo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class exercise(
    @PrimaryKey(autoGenerate = true) val exerciseId: Int = 0,
    val exerciseName: String,
    val weight: Int? = null,
    val reps: Int? = null,
    val rir: Int? = null,
)
