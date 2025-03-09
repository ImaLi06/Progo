package com.example.progo.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrincipalButton(text: String, onClick: () -> Unit, height: Int, width: Int){
   Button(
       onClick = {onClick()},
       colors = ButtonDefaults.buttonColors(
           containerColor = MaterialTheme.colorScheme.primary
       ),
       shape = RoundedCornerShape(10.dp),
       modifier = Modifier
           .size(width.dp, height.dp)
           .padding(0.dp)
   ) {
       Text(
           text = text,
           fontSize = 18.sp,
           color = MaterialTheme.colorScheme.onPrimary
       )
   }
}

@Composable
fun SecondaryButton(text: String, onClick: () -> Unit, height: Int, width: Int){
    Button(
        onClick = {onClick()},
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.size(width.dp, height.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}