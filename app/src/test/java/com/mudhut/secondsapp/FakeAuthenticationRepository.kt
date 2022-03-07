package com.mudhut.secondsapp

import com.mudhut.secondsapp.data.models.User
import com.mudhut.secondsapp.domain.respositories.IAuthenticationRepository
import com.mudhut.secondsapp.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FakeAuthenticationRepository : IAuthenticationRepository {
    override fun login(user: User): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        print("reached")
        emit(
            Resource.Success(
                data = User(
                    id = 1,
                    username = "test-user",
                    token = "thisisatesttoken"
                )
            )
        )
    }.catch {
        emit(Resource.Error(data = null, message = "Test error"))
    }.flowOn(Dispatchers.IO)

    override fun register(user: User): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        emit(
            Resource.Success(
                data = User(
                    id = 1,
                    username = "test-user",
                    token = "thisisatesttoken"
                )
            )
        )
    }.catch {
        emit(Resource.Error(data = null, message = "Test error"))
    }.flowOn(Dispatchers.IO)
}
