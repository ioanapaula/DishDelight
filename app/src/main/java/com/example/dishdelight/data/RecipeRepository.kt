
import com.example.dishdelight.data.NetworkUtils
import com.example.dishdelight.data.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class RecipeRepository {
    private val apiUrl = "https://www.themealdb.com/api/json/v1/1/filter.php?c=lamb"

    suspend fun getRecipes(): List<Recipe> {
        return withContext(Dispatchers.IO) {
            val response = NetworkUtils.get(apiUrl)
            parseRecipes(response)
        }
    }

    private fun parseRecipes(response: String): List<Recipe> {
        val recipes = mutableListOf<Recipe>()
        val jsonObject = JSONObject(response)
        val jsonArray = jsonObject.getJSONArray("meals")

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val recipe = Recipe(
                title = jsonObject.getString("strMeal"),
                imageUrl = jsonObject.getString("strMealThumb")
            )
            recipes.add(recipe)
        }

        return recipes
    }
}