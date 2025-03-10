package com.example.progo.ui.component

import android.icu.text.ListFormatter.Width
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionDialog(
    list: List<String>,
    widthField: Int,
    heightField: Int
){
    var selectedOption by remember { mutableStateOf("None") }
    var show by remember { mutableStateOf(false) }
    TextField(
        value = selectedOption,
        onValueChange = {},
        readOnly = true,
        modifier = Modifier
            .size(widthField.dp, heightField.dp)
            .clickable{
                show = true
                println("esta")
            },
        enabled = false,
        colors = TextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.onSecondary
        )
    )
    if(show){
        AlertDialog(onDismissRequest = {show = false}){
            LazyColumn(
                modifier = Modifier
                    .size(320.dp, 450.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(color = MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp),
                contentPadding = PaddingValues(20.dp)
            ) {
                items(list){ item ->
                    SecondaryButton(
                        text = item,
                        onClick = {
                            show = false
                            selectedOption = item
                        },
                        width = 280,
                        height = 50
                    )
                }
            }
        }
    }
}