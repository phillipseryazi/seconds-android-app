package com.mudhut.secondsapp.presentation.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mudhut.secondsapp.data.models.User
import com.mudhut.secondsapp.domain.respositories.IAuthenticationRepository
import com.mudhut.secondsapp.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface AuthUiState {
    var email: String
    var password: String
    var isLoading: Boolean
    var uiMessage: String?

    data class LoginUiState(
        override var email: String = "",
        override var password: String = "",
        override var isLoading: Boolean = false,
        override var uiMessage: String? = null,
        var user: User = User(),
        var isAuthenticated: Boolean = false
    ) : AuthUiState

    data class RegistrationUiState(
        override var email: String = "",
        override var password: String = "",
        override var isLoading: Boolean = false,
        override var uiMessage: String? = null,
        var navigateToLogin: Boolean = false,
        var username: String = ""
    ) : AuthUiState
}

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val repository: IAuthenticationRepository
) : ViewModel() {

    var registrationUiState = MutableStateFlow(AuthUiState.RegistrationUiState())
        private set

    var loginUiState = MutableStateFlow(AuthUiState.LoginUiState())
        private set

    fun updateLoginEmail(email: String) {
        loginUiState.value = loginUiState.value.copy(email = email)
    }

    fun updateLoginPassword(password: String) {
        loginUiState.value = loginUiState.value.copy(password = password)
    }

    fun updateRegistrationEmail(email: String) {
        registrationUiState.value = registrationUiState.value.copy(email = email)
    }

    fun updateRegistrationUsername(username: String) {
        registrationUiState.value = registrationUiState.value.copy(username = username)
    }

    fun updateRegistrationPassword(password: String) {
        registrationUiState.value = registrationUiState.value.copy(password = password)
    }

    fun resetLoginError() {
        loginUiState.value = loginUiState.value.copy(uiMessage = null)
    }

    fun resetRegistrationError() {
        registrationUiState.value = registrationUiState.value.copy(uiMessage = null)
    }


    fun login() {
        viewModelScope.launch {
            val state = loginUiState.value
            if (state.email.trim().isEmpty() ||
                state.password.trim().isEmpty() ||
                !Patterns.EMAIL_ADDRESS.matcher(state.email).matches()
            ) {
                loginUiState.value = loginUiState.value.copy(
                    uiMessage = "Please provide valid credentials"
                )
            } else {
                repository.login(
                    User(
                        email = state.email,
                        password = state.password
                    )
                ).collect {
                    when (it) {
                        is Resource.Loading -> {
                            loginUiState.value = loginUiState.value.copy(
                                isLoading = true
                            )
                        }
                        is Resource.Success -> {
                            loginUiState.value = loginUiState.value.copy(
                                isLoading = false,
                                isAuthenticated = true,
                                user = it.data!!
                            )
                        }
                        is Resource.Error -> {
                            loginUiState.value = loginUiState.value.copy(
                                isLoading = false,
                                uiMessage = it.message
                            )
                        }
                    }
                }
            }
        }
    }

    fun register() {
        viewModelScope.launch {
            val state = registrationUiState.value
            if (state.email.trim().isEmpty() ||
                state.username.trim().isEmpty() ||
                state.password.trim().isEmpty() ||
                !Patterns.EMAIL_ADDRESS.matcher(state.email).matches() ||
                state.password.length < 8
            ) {
                registrationUiState.value = registrationUiState.value.copy(
                    uiMessage = "Please provide valid credentials"
                )
            } else {
                repository.register(
                    User(
                        email = state.email,
                        username = state.username,
                        password = state.password
                    )
                ).collect {
                    when (it) {
                        is Resource.Loading -> {
                            registrationUiState.value = registrationUiState.value.copy(
                                isLoading = true
                            )
                        }
                        is Resource.Success -> {
                            registrationUiState.value = registrationUiState.value.copy(
                                isLoading = false,
                                navigateToLogin = true
                            )
                        }
                        is Resource.Error -> {
                            registrationUiState.value = registrationUiState.value.copy(
                                isLoading = false,
                                uiMessage = it.message
                            )
                        }
                    }
                }
            }
        }
    }
}
