package com.mudhut.secondsapp.domain.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface ICustomCoroutineDispatcher {
    fun main(): CoroutineDispatcher = Dispatchers.Main
    fun io(): CoroutineDispatcher = Dispatchers.IO
    fun default(): CoroutineDispatcher = Dispatchers.Default
    fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}

class CustomCoroutineDispatcher : ICustomCoroutineDispatcher