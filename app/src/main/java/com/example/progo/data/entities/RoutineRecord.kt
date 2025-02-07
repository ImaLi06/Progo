package com.example.progo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoutineRecord (
    @PrimaryKey(autoGenerate = true) val routineRecordId: Int,
    val routineName: String,
    val date: String,
    val time: Int
)