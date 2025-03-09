package com.example.progo.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun PrimaryText(text: String, fontSize: Int){
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = fontSize.sp,
    )
}

@Composable
fun SecondaryText(text: String, fontSize: Int){
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onSecondary,
        fontSize = fontSize.sp
    )
}