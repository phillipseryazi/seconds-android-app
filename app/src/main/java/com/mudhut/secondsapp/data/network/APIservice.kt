package com.mudhut.secondsapp.data.network

import com.mudhut.secondsapp.data.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIservice {

    @POST("auth/login/")
    suspend fun login(@Body user: Map<String, User>): Response<Map<String, User>>

    @POST("auth/signup/")
    suspend fun signup(@Body user: Map<String, User>): Response<Map<String, User>>
}
