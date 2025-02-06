package com.example.progo.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun primaryTextTemplate(modifier: Modifier, primaryText: String){
    Text(
        text = primaryText,
        color = MaterialTheme.colorScheme.secondary,
        fontSize = 25.sp,
        modifier = modifier
    )
}

@Composable
fun SecondaryTextTemplate(secondaryText: String, fontSize: Int){
    Text(
        text = secondaryText,
        color = Color.Green,
        fontSize = fontSize.sp
    )
}