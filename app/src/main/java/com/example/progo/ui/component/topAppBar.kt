package com.example.progo.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.progo.ui.viewmodel.ExerciseRoutineSharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgoTopBar(
    navController: NavController,
    sharedViewModel: ExerciseRoutineSharedViewModel,
    route: String,
){
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        if(route == "edit"){
                            sharedViewModel.changeRoutineState(false)
                        }
                        sharedViewModel.deleteLastTitle()
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "GoBack",
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
                Text("Progo")
                Spacer(modifier = Modifier.size(50.dp))
            }
        }
    )
}