package com.demo.di.data

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.weatherusa.data.remote.api.UserApi
import com.weatherusa.data.remote.api.WeatherApi
import com.weatherusa.data.remote.interceptor.ApiInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import net.weatherusa.di.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import weatherusa.domain.preferences.SettingsPreferences
import weatherusa.domain.preferences.UserPreferences
import weatherusa.domain.usecase.auth.UserLogoutUseCase
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

const val GENERAL_TIMEOUT = 30L

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideChucker(@ApplicationContext context: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context)
            .collector(ChuckerCollector(context))
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    @Singleton
    fun provideApiInterceptor(userPreferences: UserPreferences, userLogoutUseCase: UserLogoutUseCase): ApiInterceptor = ApiInterceptor(userPreferences, userLogoutUseCase)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        chuckerInterceptor: ChuckerInterceptor,
        settingsPreferences: SettingsPreferences,
        apiInterceptor: ApiInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(GENERAL_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(GENERAL_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(GENERAL_TIMEOUT, TimeUnit.SECONDS)
            .run {
                if (BuildConfig.TEST_OPTION_ENABLED) {
                    val chuckerState = runBlocking { settingsPreferences.chuckerState.first() }
                    if (chuckerState) addInterceptor(chuckerInterceptor) else this
                } else this
            }
            .run {
                if (BuildConfig.DISABLE_SSL_VERIFICATION) hostnameVerifier { _, _ -> true }
                else this
            }
            .addInterceptor(apiInterceptor)

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gsonBuilder: Gson): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .build()
    }

    @Provides
    @Singleton
    @Named("v1.2")
    fun provideRetrofitV1_2(okHttpClient: OkHttpClient, gsonBuilder: Gson): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.API_URL_1_2)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .build()
    }

    @Provides
    @Singleton
    @Named("v1.3")
    fun provideRetrofitV1_3(okHttpClient: OkHttpClient, gsonBuilder: Gson): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.API_URL_1_3)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    @Singleton
    @Named("v1.2")
    fun provideWeatherApiV1_2(@Named("v1.2") retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    @Named("v1.3")
    fun provideWeatherApiV1_3(@Named("v1.3") retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }
}