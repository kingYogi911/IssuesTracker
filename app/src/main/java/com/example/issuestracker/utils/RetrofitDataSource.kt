package com.example.issuestracker.utils

import com.example.issuestracker.data.Comments
import com.example.issuestracker.data.Issue
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitDataSource {
    @GET("repos/square/okhttp/issues")
    suspend fun getCardsData(): Response<ArrayList<Issue>>

    @GET("{url}")
    suspend fun getComments(@Path(value = "url",encoded = true) url: String): Response<ArrayList<Comments>>
}