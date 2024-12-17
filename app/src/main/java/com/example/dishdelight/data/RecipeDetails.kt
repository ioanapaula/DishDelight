package com.example.dishdelight.data

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class RecipeDetailsResponse(
    val meals: List<RecipeDetails>
)

data class RecipeDetails(
    @SerializedName("idMeal") val id: String,
    @SerializedName("strMeal") val title: String,
    @SerializedName("strCategory") val category: String,
    @SerializedName("strArea") val area: String,
    @SerializedName("strTags") val tags: String?,
    @SerializedName("strInstructions") val instructions: String,
    @SerializedName("strMealThumb") val imageUrl: String,
    @SerializedName("strYoutube") val youtubeUrl: String?,
    @SerializedName("strSource") val recipeSourceUrl: String?,

    val ingredients: List<Ingredient>
)

data class Ingredient(
    val name: String,
    val measure: String,
    //val imageUrl: String
)

class RecipeDetailsDeserializer : JsonDeserializer<RecipeDetails> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: java.lang.reflect.Type?,
        context: JsonDeserializationContext
    ): RecipeDetails {
        val jsonObject = json.asJsonObject
        val ingredients = mutableListOf<Ingredient>()
        for (i in 1..20) {
            val ingredientName = if (jsonObject.get("strIngredient$i").isJsonNull) "" else jsonObject.get("strIngredient$i")?.asString
            val ingredientMeasure = if (jsonObject.get("strMeasure$i").isJsonNull) "" else jsonObject.get("strMeasure$i")?.asString
            if (!ingredientName.isNullOrEmpty()) {
                ingredients.add(Ingredient(ingredientName, ingredientMeasure ?: ""))
            }
        }

        return RecipeDetails(
            id = jsonObject.get("idMeal").asString,
            title = jsonObject.get("strMeal").asString,
            category = jsonObject.get("strCategory").asString,
            area = jsonObject.get("strArea").asString,
            tags = if (jsonObject.get("strTags").isJsonNull) "" else jsonObject.get("strTags")?.asString,
            instructions = jsonObject.get("strInstructions").asString,
            imageUrl = jsonObject.get("strMealThumb").asString,
            youtubeUrl = if (jsonObject.get("strYoutube").isJsonNull) "" else jsonObject.get("strYoutube")?.asString,
            recipeSourceUrl = if (jsonObject.get("strSource").isJsonNull) "" else jsonObject.get("strSource")?.asString,
            ingredients = ingredients
        )
    }
}