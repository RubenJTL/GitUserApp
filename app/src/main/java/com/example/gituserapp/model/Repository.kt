package com.example.gituserapp.model

import com.google.gson.annotations.SerializedName

data class Repository(
    val name: String,
    val url: String,
    val description : String,
    val PR_count : Int
)