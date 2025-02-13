package com.example.progo.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDataTextFieldTemplate(value: String, onValueChange:(String) -> Unit){
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        modifier = Modifier.width(200.dp), // Only constrain width
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent // Ensures no background color if desired
        )
    )
}

@Composable
fun PrincipalTextLabel(value: String, onValueChange: (String) -> Unit, height: Int, width: Int){
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        modifier = Modifier.size(width.dp, height.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent
        )
    )
}

@Composable
fun SecondaryTextLabel(value: String, onValueChange: (String) -> Unit, height: Int, width: Int){
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        modifier = Modifier.size(width.dp, height.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent
        )
    )
}