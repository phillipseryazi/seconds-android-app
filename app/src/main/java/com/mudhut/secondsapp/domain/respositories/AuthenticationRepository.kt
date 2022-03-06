package com.mudhut.secondsapp.domain.respositories

import com.mudhut.secondsapp.data.models.User
import com.mudhut.secondsapp.data.network.APIservice
import com.mudhut.secondsapp.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val apiService: APIservice
) : IAuthenticationRepository {
    private val tag = "AuthenticationRepository"
    override fun login(user: User): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        val response = apiService.login(mapOf("user" to user))
        if (response.isSuccessful) {
            val authUser = response.body()?.get("user")?.let {
                User(
                    username = it.username,
                    token = it.token
                )
            }
            emit(Resource.Success(data = authUser!!))
        } else {
            throw IllegalStateException("Unable to login, make sure your credentials are correct")
        }
    }.catch {
        emit(Resource.Error(data = null, message = it.message ?: "Something went wrong"))
    }.flowOn(Dispatchers.IO)

    override fun register(user: User): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        val response = apiService.signup(mapOf("user" to user))
        if (response.isSuccessful) {
            val authUser = response.body()?.get("user")?.let {
                User(id = it.id, username = it.username)
            }
            emit(Resource.Success(data = authUser ?: User()))
        } else {
            throw IllegalStateException("User registration failed")
        }
    }.catch {
        emit(Resource.Error(data = null, message = it.message ?: "Something went wrong"))
    }.flowOn(Dispatchers.IO)
}
