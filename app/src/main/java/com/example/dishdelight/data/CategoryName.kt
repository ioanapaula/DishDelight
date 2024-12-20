package com.example.dishdelight.data

import com.google.gson.annotations.SerializedName

data class CategoryNamesResponse(
    val meals: List<CategoryName>
)

data class CategoryName(
    @SerializedName("strCategory") val title: String?
)