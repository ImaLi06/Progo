package com.example.progo.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.progo.data.entities.Exercise

@Composable
fun InputTemplate(item: Exercise){
    var aux: String
    Column(modifier = Modifier
        .clip(shape = RoundedCornerShape(15.dp))
        .background(color = MaterialTheme.colorScheme.secondary)
        .width(350.dp),
    ) {
        Spacer(modifier = Modifier.size(25.dp, 10.dp))
        Row{
            Spacer(modifier = Modifier.size(25.dp, 10.dp))
            SecondaryTextTemplate(item.exerciseName, 20)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.size(10.dp))
            Column(
                modifier = Modifier.width(50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("wexrep")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text("Peso")
                Spacer(modifier = Modifier.size(10.dp))
                SecondaryTextLabel("", {aux = it}, 30, 100)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text("Repeticiones")
                Spacer(modifier = Modifier.size(10.dp))
                SecondaryTextLabel("", {aux = it}, 30, 100)
            }
        }
        Spacer(modifier = Modifier.size(25.dp, 10.dp))
    }
}