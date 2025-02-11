package com.example.progo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exercise(
    @PrimaryKey(autoGenerate = false) val exerciseName: String,
    val muscle: String = "",
    val type: String = ""
)
