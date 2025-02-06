package com.example.progo.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class navigationItems(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Workout : navigationItems(
        route = "workoutIn",
        title = "Workout",
        icon = Icons.Default.Home
    )

    object Profile : navigationItems(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    )

    object Information : navigationItems(
        route = "information",
        title = "Information",
        icon = Icons.Default.Settings
    )
}