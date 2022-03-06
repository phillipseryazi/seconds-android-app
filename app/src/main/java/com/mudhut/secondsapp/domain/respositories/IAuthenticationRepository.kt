package com.mudhut.secondsapp.domain.respositories

import com.mudhut.secondsapp.data.models.User
import com.mudhut.secondsapp.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IAuthenticationRepository {
    fun login(user: User): Flow<Resource<User>>
    fun register(user: User): Flow<Resource<User>>
}
