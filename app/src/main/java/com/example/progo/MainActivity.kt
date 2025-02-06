package com.example.progo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.progo.ui.navigationScreens.ProgoRootNavigation
import com.example.progo.ui.theme.ProgoTheme
import com.example.progo.ui.viewmodel.ExerciseRoutineViewModel

class MainActivity : ComponentActivity() {
    val ViewModel by viewModels<ExerciseRoutineViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return ExerciseRoutineViewModel(application) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProgoTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Black)
                ) {
                    ProgoRootNavigation(
                        navController = rememberNavController(),
                        viewModel = ViewModel
                    )
                }
            }
        }
    }
}