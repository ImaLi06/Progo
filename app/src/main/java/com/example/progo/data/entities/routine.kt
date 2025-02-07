package com.example.progo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class routine(
    @PrimaryKey(autoGenerate = false) val routineName: String,
    val weekDay: String? = null,
)