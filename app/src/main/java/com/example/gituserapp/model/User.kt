package com.example.gituserapp.model
import com.google.gson.annotations.SerializedName
data class User(
    @SerializedName("login") val login: String,
    @SerializedName("name") val name: String,
    @SerializedName("location") val location: String,
    @SerializedName("avatarUrl") val avatarUrl: String
)