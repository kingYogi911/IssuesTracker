package com.example.issuestracker.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


class RetrofitHelper {
    companion object {
        val BASE_URL="https://api.github.com/"
        private var instance: RetrofitDataSource? = null
        fun getServiceInstance(): RetrofitDataSource {
            return if (instance != null) {
                instance!!
            } else {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level=HttpLoggingInterceptor.Level.BODY
                val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(RetrofitDataSource::class.java).also {
                        instance = it
                    }
            }
        }
    }
}