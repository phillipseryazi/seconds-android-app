package com.mudhut.secondsapp.presentation.viewmodels

import com.google.common.truth.Truth.assertThat
import com.mudhut.secondsapp.FakeAuthenticationRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)
class AuthenticationViewModelTest {
    private lateinit var viewModel: AuthenticationViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = AuthenticationViewModel(FakeAuthenticationRepository())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `test login`(): Unit = runBlocking {
        launch {
            viewModel.updateLoginEmail("testuser@gmail.com")
            viewModel.updateLoginPassword("password1234")
            viewModel.login()

//            assertThat(viewModel.loginUiState.value.user.username).isEqualTo("test-user")
        }
    }
}
