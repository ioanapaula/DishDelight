package com.example.dishdelight.data

import com.google.gson.annotations.SerializedName

data class CategoriesResponse(
    val categories: List<Category>
)

data class Category(
    @SerializedName("idCategory") val id: String?,
    @SerializedName("strCategory") val title: String?,
    @SerializedName("strCategoryThumb") val imageUrl: String?,
    @SerializedName("strCategoryDescription") val details: String?
)