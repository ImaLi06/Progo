package com.example.progo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class routine(
    @PrimaryKey(autoGenerate = true) val routineId: Int = 0,
    val routineName: String,
    val executionTime: Int? = null,
    val weekDay: String? = null,
    val date: Int? = null
)