package com.example.progo.ui.screens.profileScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.progo.ui.component.PrimaryTextTemplate
import com.example.progo.ui.component.SecondaryTextTemplate

@Composable
fun UserProfileHeader(){
    Row (
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        Canvas (
            modifier = Modifier
                .size(110.dp)
        ){
            drawCircle(
                color = Color.Black,
                radius = 55.dp.toPx()
            )
        }
        Column(modifier = Modifier.padding(10.dp, 0.dp)) {
            PrimaryTextTemplate(
                modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 10.dp),
                "Nombre"
            )
            Row {
                SecondaryTextTemplate(
                    "Edad",
                    20
                )
                SecondaryTextTemplate(
                    "Años Entrenados",
                    20
                )
            }
        }
    }
}

@Composable
fun ProfileScreen(paddingValues: PaddingValues){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(color = MaterialTheme.colorScheme.background)
    ){
        UserProfileHeader()
    }
}