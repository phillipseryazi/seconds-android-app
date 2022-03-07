package com.mudhut.secondsapp.domain.respositories

import com.google.common.truth.Truth.assertThat
import com.mudhut.secondsapp.data.models.User
import com.mudhut.secondsapp.data.network.APIservice
import com.mudhut.secondsapp.domain.utils.Resource
import com.mudhut.secondsapp.enqueueResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class AuthenticationRepositoryTest {
    private val mockWebServer = MockWebServer()

    private lateinit var repository: AuthenticationRepository
    private lateinit var retrofitInstance: APIservice
    private lateinit var client: OkHttpClient


    @Before
    fun setUp() {
        mockWebServer.start(8080)

        client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build()

        retrofitInstance = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIservice::class.java)

        repository = AuthenticationRepository(retrofitInstance)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test register user success`() = runBlocking {
        mockWebServer.enqueueResponse("register_user_response.json", 201)
        val user = User(
            username = "test-user",
            email = "test-user@gmail.com",
            password = "password1234"
        )
        repository.register(user).collect {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    assertThat(it.data?.username).isEqualTo("test-user")
                }
                is Resource.Error -> {}
            }
        }
    }

    @Test
    fun `test login user success`() = runBlocking {
        mockWebServer.enqueueResponse("login_user_response.json", 200)
        val user = User(
            username = "test-user",
            email = "test-user@gmail.com",
            password = "password1234"
        )
        repository.login(user).collect {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    assertThat(it.data?.username).isEqualTo("test-user")
                }
                is Resource.Error -> {}
            }
        }
    }
}
