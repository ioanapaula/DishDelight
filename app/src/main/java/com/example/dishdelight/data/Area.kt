package com.example.dishdelight.data

import com.google.gson.annotations.SerializedName

data class AreasResponse(
    val meals: List<AreaName>
)

data class AreaName(
    @SerializedName("strArea") val title: String?
)