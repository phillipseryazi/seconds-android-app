package com.mudhut.secondsapp.presentation.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mudhut.secondsapp.R
import com.mudhut.secondsapp.presentation.viewmodels.AuthUiState
import com.mudhut.secondsapp.presentation.views.common.ProgressBarComposable
import com.mudhut.secondsapp.presentation.views.common.SecondsButton
import com.mudhut.secondsapp.presentation.views.common.SecondsTextField
import com.mudhut.secondsapp.ui.theme.SecondsAppTheme

@Composable
fun RegistrationScreen(
    navigateToLogin: () -> Unit,
    updateUsername: (String) -> Unit,
    updateEmail: (String) -> Unit,
    updatePassword: (String) -> Unit,
    onRegistrationClick: () -> Unit,
    resetError: () -> Unit,
    uiState: AuthUiState.RegistrationUiState
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = uiState.navigateToLogin, block = {
        if (uiState.navigateToLogin) {
            navigateToLogin()
        }
    })

    LaunchedEffect(key1 = uiState.uiMessage, block = {
        uiState.uiMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            resetError()
        }
    })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        if (uiState.isLoading) {
            ProgressBarComposable(modifier = Modifier.fillMaxSize())
        } else {
            RegistrationFormComposable(
                modifier = Modifier.fillMaxSize(),
                navigateToLogin = navigateToLogin,
                updateUsername = updateUsername,
                updateEmail = updateEmail,
                updatePassword = updatePassword,
                onRegistrationClick = onRegistrationClick,
                uiState = uiState
            )
        }
    }
}

@Composable
fun RegistrationFormComposable(
    modifier: Modifier,
    navigateToLogin: () -> Unit,
    updateUsername: (String) -> Unit,
    updateEmail: (String) -> Unit,
    updatePassword: (String) -> Unit,
    onRegistrationClick: () -> Unit,
    uiState: AuthUiState.RegistrationUiState
) {
    Column(modifier = modifier) {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = {
                Text(
                    stringResource(R.string.text_register),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Blue,
                    textAlign = TextAlign.Center,
                )
            },
            navigationIcon = {
                IconButton(onClick = navigateToLogin) {
                    Icon(
                        modifier = Modifier
                            .size(ButtonDefaults.IconSize),
                        painter = painterResource(
                            id = R.drawable.ic_arrow_back
                        ),
                        tint = Color.Blue,
                        contentDescription = null
                    )
                }
            },
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        )

        SecondsTextField(
            modifier = Modifier
                .padding(top = 32.dp)
                .width(240.dp)
                .align(Alignment.CenterHorizontally),
            value = uiState.username,
            label = stringResource(id = R.string.text_username),
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = updateUsername
        )

        SecondsTextField(
            modifier = Modifier
                .padding(top = 16.dp)
                .width(240.dp)
                .align(Alignment.CenterHorizontally),
            value = uiState.email,
            label = stringResource(id = R.string.text_email),
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = updateEmail
        )

        SecondsTextField(
            modifier = Modifier
                .padding(top = 16.dp)
                .width(240.dp)
                .align(Alignment.CenterHorizontally),
            value = uiState.password,
            label = stringResource(id = R.string.text_password),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = updatePassword
        )

        SecondsButton(
            modifier = Modifier
                .padding(top = 16.dp)
                .width(100.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.text_register),
            onButtonClick = onRegistrationClick
        )
    }
}

@Preview
@Composable
fun RegistrationScreenPreview() {
    SecondsAppTheme {
        RegistrationScreen(
            navigateToLogin = {},
            updateUsername = {},
            updateEmail = {},
            updatePassword = {},
            onRegistrationClick = {},
            resetError = {},
            uiState = AuthUiState.RegistrationUiState()
        )
    }
}
