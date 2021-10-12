package com.example.issuestracker.data;

import android.graphics.Color
import com.google.gson.annotations.SerializedName

data class Label(
    @SerializedName("name")
    val name: String,
    @SerializedName("color")
    val color: String
) {

}
