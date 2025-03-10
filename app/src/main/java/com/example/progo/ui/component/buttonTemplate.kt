package com.example.progo.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownButton(list: List<String>, width: Int, height: Int){
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(list[0]) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded},
        ) {
            TextField(
                modifier = Modifier
                    .size(width.dp, height.dp)
                    .menuAnchor(),
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
                ),
                trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)}
            )
            DropdownMenu(
                expanded = expanded, onDismissRequest = { expanded = false},
                modifier = Modifier.exposedDropdownSize()
            ) {
                list.forEachIndexed{ index, text ->
                    DropdownMenuItem(
                        text = { Text(text)},
                        onClick = {
                            selectedOption = list[index]
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}