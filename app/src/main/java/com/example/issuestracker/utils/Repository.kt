package com.example.issuestracker.utils

import com.example.issuestracker.data.Comments
import com.example.issuestracker.data.Issue

class Repository(private val retrofitDataSource: RetrofitDataSource) {
    @Throws(Exception::class)
    suspend fun getIssues(): ArrayList<Issue>? {
        try {
            return retrofitDataSource.getCardsData().body()
        } catch (e: Exception) {
            throw e
        }
    }

    @Throws(Exception::class)
    suspend fun getComments(url:String):ArrayList<Comments>?{
        try{
            return retrofitDataSource.getComments(url).body()
        }catch (e:Exception){
            throw e;
        }
    }
}