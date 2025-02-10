package com.example.progo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class exercise(
    @PrimaryKey(autoGenerate = false) val exerciseName: String,
    val rm: List<Float> = emptyList(),
    val muscle: String = "",
    val type: String = ""
)
