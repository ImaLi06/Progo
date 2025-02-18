package com.example.progo.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeSharedViewModel(): ViewModel(){
    private val _modeWorkout = MutableStateFlow("a")
    val modeWorkout = _modeWorkout.asStateFlow()
    private val _createdRoutineName = MutableStateFlow("")
    val createdRoutineName = _createdRoutineName.asStateFlow()

    fun establishMode(mode: String){
        _modeWorkout.value = mode
        println(_modeWorkout.value)
    }

    fun establishCreatedRoutineName(name: String){
        _createdRoutineName.value = name
    }
}