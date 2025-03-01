package com.example.progo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.progo.data.navigationItems
import com.example.progo.ui.navigationScreens.HomeNavGraph
import com.example.progo.ui.viewmodel.ExerciseRoutineViewModel

@Composable
fun HomeScreen(navController: NavHostController = rememberNavController(), viewModel: ExerciseRoutineViewModel){
    Scaffold (
        topBar = { TopBar(navController)},
        bottomBar = { bottomBar(navController = navController) }
    ){
        HomeNavGraph(navController = navController, paddingValues = it, viewModel = viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController){
    val navigationItemsList = listOf(
        navigationItems.Information,
        navigationItems.Workout,
        navigationItems.Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val topBarDestination = navigationItemsList.any { it.route == currentDestination?.route }
    if(topBarDestination){
        TopAppBar(
            title = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Progo")
                }
            },
            modifier = Modifier
                .background(color = Color.Green)
        )
    }
}

@Composable
fun bottomBar(navController: NavHostController){
    val navigationItemsList = listOf(
        navigationItems.Information,
        navigationItems.Workout,
        navigationItems.Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = navigationItemsList.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        NavigationBar {
            navigationItemsList.forEach { screen ->
                AddItem(
                    navigationItems = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }

}

@Composable
fun RowScope.AddItem(
    navigationItems: navigationItems,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        label = {
            Text(text = navigationItems.title)
        },
        icon = {
            Icon(
                imageVector = navigationItems.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == navigationItems.route
        } == true,
        onClick = {
            navController.navigate(navigationItems.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}