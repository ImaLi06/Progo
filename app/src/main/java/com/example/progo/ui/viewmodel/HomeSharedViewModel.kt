package com.example.progo.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.progo.data.navigationItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeSharedViewModel(): ViewModel(){
    private val _modeWorkout = MutableStateFlow("")
    val modeWorkout = _modeWorkout.asStateFlow()

    fun establishMode(mode: String){
        when(mode){
            "edit" -> _modeWorkout.value = "edit"
            "start" -> _modeWorkout.value = "start"
        }
    }
}