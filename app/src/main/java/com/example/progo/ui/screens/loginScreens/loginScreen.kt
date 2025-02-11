package com.example.progo.ui.screens.loginScreens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.progo.R
import com.example.progo.ui.component.PrincipalButton
import com.example.progo.ui.component.UserDataTextFieldTemplate
import com.example.progo.ui.component.SecondaryTextTemplate

@Composable
fun insertUserData(start: () -> Unit){
    var userName by remember { mutableStateOf("") }
    var userBirthday by remember { mutableStateOf("")}
    var userTrainingTime by remember {mutableStateOf("")}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Canvas(
            modifier = Modifier
                .size(140.dp)
        ) {
            drawCircle(
                color = Color.Black,
                radius = 70.dp.toPx()
            )
        }
        Spacer(modifier = Modifier.size(10.dp, 20.dp))

        //Name input field
        insertUserName(userData = userName, onValueChange = {userName = it})
        Spacer(modifier = Modifier.size(10.dp, 20.dp))

        //Birthday input field
        insertUserBirthday(userData = userBirthday, onValueChange = {userBirthday = it})
        Spacer(modifier = Modifier.size(10.dp, 20.dp))

        //Time training input field
        insertUserTrainingTime(userData = userTrainingTime, onValueChange = {userTrainingTime = it})

        //Boton para iniciar
        Spacer(modifier = Modifier.size(10.dp, 30.dp))
        registerUserButton(start)
    }
}

//Input fields
@Composable
fun insertUserTrainingTime(userData: String, onValueChange: (String) -> Unit) {
    SecondaryTextTemplate(
        stringResource(R.string.training_user_years),
        20
    )
    UserDataTextFieldTemplate(
        value = userData,
        onValueChange = onValueChange
    )
}

@Composable
fun insertUserBirthday(userData: String, onValueChange: (String) -> Unit) {
    SecondaryTextTemplate(
        stringResource(R.string.user_birthday),
        20
    )
    UserDataTextFieldTemplate(
        value = userData,
        onValueChange = onValueChange
    )
}

@Composable
fun insertUserName(userData: String, onValueChange: (String) -> Unit) {
    SecondaryTextTemplate(
        stringResource(R.string.user_name),
        20
    )
    UserDataTextFieldTemplate(
        value = userData,
        onValueChange = onValueChange
    )
}

@Composable
fun registerUserButton(start: () -> Unit){
    PrincipalButton("Iniciar", start, 50, 60)
}