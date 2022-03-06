package com.mudhut.secondsapp.presentation.views

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mudhut.secondsapp.R
import com.mudhut.secondsapp.presentation.views.common.SecondsButton
import com.mudhut.secondsapp.ui.theme.SecondsAppTheme

@Composable
fun HomeScreen(
    username: String
) {
    val activity = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = {
                Text(
                    stringResource(R.string.text_home),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Blue,
                    textAlign = TextAlign.Center,
                )
            },
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        )

        Text(
            "Welcome\n$username",
            modifier = Modifier
                .padding(top = 32.dp)
                .align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )

        SecondsButton(
            modifier = Modifier
                .width(150.dp)
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.text_logout),
            onButtonClick = { activity.finish() }
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    SecondsAppTheme {
        HomeScreen(
            username = ""
        )
    }
}
