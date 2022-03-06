package com.mudhut.secondsapp.presentation.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
fun LoginScreen(
    navigateToRegistration: () -> Unit,
    navigateToHome: (String) -> Unit,
    updateEmail: (String) -> Unit,
    updatePassword: (String) -> Unit,
    onLoginClick: () -> Unit,
    resetError: () -> Unit,
    uiState: AuthUiState.LoginUiState
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = uiState.isAuthenticated, block = {
        if (uiState.isAuthenticated) {
            navigateToHome(uiState.user.username)
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
            LoginFormComposable(
                modifier = Modifier.fillMaxSize(),
                navigateToRegistration = navigateToRegistration,
                updateEmail = updateEmail,
                updatePassword = updatePassword,
                onLoginClick = onLoginClick,
                uiState = uiState
            )
        }
    }
}

@Composable
fun LoginFormComposable(
    modifier: Modifier,
    navigateToRegistration: () -> Unit,
    updateEmail: (String) -> Unit,
    updatePassword: (String) -> Unit,
    onLoginClick: () -> Unit,
    uiState: AuthUiState.LoginUiState
) {
    Column(modifier = modifier) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        SecondsTextField(
            modifier = Modifier
                .padding(top = 32.dp)
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
            text = stringResource(id = R.string.text_login),
            onButtonClick = onLoginClick
        )

        Text(
            stringResource(id = R.string.text_register_here),
            modifier = Modifier
                .padding(top = 16.dp)
                .clickable {
                    navigateToRegistration()
                }
                .align(Alignment.CenterHorizontally),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    SecondsAppTheme {
        LoginScreen(
            navigateToRegistration = {},
            navigateToHome = {},
            updateEmail = {},
            updatePassword = {},
            onLoginClick = {},
            resetError = {},
            uiState = AuthUiState.LoginUiState()
        )
    }
}
