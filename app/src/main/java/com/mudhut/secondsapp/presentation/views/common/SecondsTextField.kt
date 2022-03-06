package com.mudhut.secondsapp.presentation.views.common

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun SecondsTextField(
    modifier: Modifier,
    value: String,
    label: String,
    visualTransformation: VisualTransformation,
    keyboardOptions: KeyboardOptions,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        label = { Text(label) },
        value = value,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        onValueChange = onValueChange
    )
}
