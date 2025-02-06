package com.example.progo.data.entities

import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["exerciseId", "routineId"],
    indices = [Index(value = ["exerciseId"]), Index(value = ["routineId"])]
)
data class exerciseRoutineCrossRef(
    val exerciseId: Int,
    val routineId: Int
)
