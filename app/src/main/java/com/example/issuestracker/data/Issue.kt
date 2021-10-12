package com.example.issuestracker.data

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class Issue(
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val lastUpdateTime: String,
    @SerializedName("body")
    val body: String,
    @SerializedName("user")
    val user: User,
    @SerializedName("comments_url")
    val comment_url: String,
    @SerializedName("labels")
    val labels: ArrayList<Label>
) {
    val updated_at
        get():String {
            lastUpdateTime.split("T").let {
                return SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH).parse(it[0]).let {
                    SimpleDateFormat("mm-dd-yyyy", Locale.ENGLISH).format(it!!)
                }
            }
        }
}