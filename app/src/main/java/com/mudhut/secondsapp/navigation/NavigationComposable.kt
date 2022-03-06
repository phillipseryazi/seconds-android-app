package com.mudhut.secondsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mudhut.secondsapp.domain.utils.HOME_SCREEN
import com.mudhut.secondsapp.domain.utils.LOGIN_SCREEN
import com.mudhut.secondsapp.domain.utils.REGISTRATION_SCREEN
import com.mudhut.secondsapp.presentation.viewmodels.AuthenticationViewModel
import com.mudhut.secondsapp.presentation.viewmodels.HomeViewModel
import com.mudhut.secondsapp.presentation.views.HomeScreen
import com.mudhut.secondsapp.presentation.views.LoginScreen
import com.mudhut.secondsapp.presentation.views.RegistrationScreen

@Composable
fun NavigationComposable() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = LOGIN_SCREEN,
        builder = {
            composable(LOGIN_SCREEN) {
                val viewModel = hiltViewModel<AuthenticationViewModel>()
                LoginScreen(
                    navigateToRegistration = {
                        navController.navigate(REGISTRATION_SCREEN)
                    },
                    navigateToHome = { username ->
                        navController.navigate("$HOME_SCREEN/$username") {
                            popUpTo("$HOME_SCREEN/{username}")
                        }
                    },
                    updateEmail = {
                        viewModel.updateLoginEmail(it)
                    },
                    updatePassword = {
                        viewModel.updateLoginPassword(it)
                    },
                    onLoginClick = {
                        viewModel.login()
                    },
                    resetError = {
                        viewModel.resetLoginError()
                    },
                    uiState = viewModel.loginUiState.collectAsState().value
                )
            }

            composable(REGISTRATION_SCREEN) {
                val viewModel = hiltViewModel<AuthenticationViewModel>()
                RegistrationScreen(
                    navigateToLogin = {
                        navController.popBackStack()
                    },
                    updateUsername = {
                        viewModel.updateRegistrationUsername(it)
                    },
                    updateEmail = {
                        viewModel.updateRegistrationEmail(it)
                    },
                    updatePassword = {
                        viewModel.updateRegistrationPassword(it)
                    },
                    onRegistrationClick = {
                        viewModel.register()
                    },
                    resetError = {
                        viewModel.resetRegistrationError()
                    },
                    uiState = viewModel.registrationUiState.collectAsState().value
                )
            }

            composable(
                "$HOME_SCREEN/{username}",
                arguments = listOf(navArgument("username") {
                    type = NavType.StringType
                })
            ) {
                val viewModel = hiltViewModel<HomeViewModel>()
                HomeScreen(
                    username = viewModel.usernameState.value ?: ""
                )
            }
        }
    )
}
