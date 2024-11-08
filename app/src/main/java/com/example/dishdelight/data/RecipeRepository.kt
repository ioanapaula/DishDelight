
import com.example.dishdelight.data.Category
import com.example.dishdelight.data.NetworkUtils
import com.example.dishdelight.data.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class RecipeRepository {
    private val testApiUrl = "https://www.themealdb.com/api/json/v1/1/filter.php?c=lamb"
    private val categoriesUrl = "https://www.themealdb.com/api/json/v1/1/categories.php"

    suspend fun getCategories(): List<Category> {
        return withContext(Dispatchers.IO) {
            val response = NetworkUtils.get(categoriesUrl)
            parseCategories(response)
        }
    }

    private fun parseCategories(response: String): List<Category> {
        val categories = mutableListOf<Category>()
        val jsonObject = JSONObject(response)
        val jsonArray = jsonObject.getJSONArray("categories")

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val category = Category(
                title = jsonObject.getString("strCategory"),
                details = jsonObject.getString("strCategoryDescription"),
                imageUrl = jsonObject.getString("strCategoryThumb")
            )

            categories.add(category)
        }

        return categories
    }

    suspend fun getRecipes(): List<Recipe> {
        return withContext(Dispatchers.IO) {
            val response = NetworkUtils.get(testApiUrl)
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