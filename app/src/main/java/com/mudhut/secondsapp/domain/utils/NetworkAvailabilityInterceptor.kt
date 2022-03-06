package com.mudhut.secondsapp.domain.utils

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkAvailabilityInterceptor @Inject constructor(var context: Context) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!context.isNetworkAvailable()) {
            throw NoNetworkException("You have no network access")
        }
        return chain.proceed(chain.request())
    }
}
