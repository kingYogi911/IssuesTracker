package com.example.issuestracker.data

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login")
    val userName: String,
    @SerializedName("avatar_url")
    val avatar_url: String
) {
    public val nameSpanned
        get():CharSequence {
            return SpannableStringBuilder(userName).apply {
                setSpan(
                    ForegroundColorSpan(Color.BLACK),
                    0,
                    userName.length,
                    SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }
        };
}