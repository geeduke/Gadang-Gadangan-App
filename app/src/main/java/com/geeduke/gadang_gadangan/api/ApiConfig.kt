package com.geeduke.gadang_gadangan.api

import com.geeduke.gadang_gadangan.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiConfig {
    companion object{
        fun getApiService():ApiService{
            val loggingInterceptor = if(BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            val cllient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
            val retrofit =Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(cllient).build()
            return retrofit.create(ApiService::class.java)
        }
    }
}