package com.example.gituserapp.model

import com.google.gson.annotations.SerializedName

data class Repository(
    @SerializedName("url") val url: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("PR_count") val PR_count: Int
)