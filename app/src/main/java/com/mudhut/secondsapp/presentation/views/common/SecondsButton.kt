package com.mudhut.secondsapp.presentation.views.common

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SecondsButton(
    modifier: Modifier,
    text: String,
    onButtonClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onButtonClick
    ) {
        Text(text)
    }
}
