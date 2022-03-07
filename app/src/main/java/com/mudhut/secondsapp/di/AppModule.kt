package com.mudhut.secondsapp.di

import android.content.Context
import com.mudhut.secondsapp.data.network.APIservice
import com.mudhut.secondsapp.domain.respositories.AuthenticationRepository
import com.mudhut.secondsapp.domain.respositories.IAuthenticationRepository
import com.mudhut.secondsapp.domain.utils.BASE_URL
import com.mudhut.secondsapp.domain.utils.CustomCoroutineDispatcher
import com.mudhut.secondsapp.domain.utils.ICustomCoroutineDispatcher
import com.mudhut.secondsapp.domain.utils.NetworkAvailabilityInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesNetworkInterceptor(@ApplicationContext context: Context) =
        NetworkAvailabilityInterceptor(context)

    @Singleton
    @Provides
    fun providesHttpClient(networkInterceptor: NetworkAvailabilityInterceptor): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(networkInterceptor)
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(client: OkHttpClient): APIservice {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
        return retrofit.create(APIservice::class.java)
    }

    @Singleton
    @Provides
    fun providesCustomCoroutineDispatcher() =
        CustomCoroutineDispatcher() as ICustomCoroutineDispatcher

    @Provides
    fun providesAuthenticationRepository(apiService: APIservice): IAuthenticationRepository {
        return AuthenticationRepository(apiService)
    }
}
