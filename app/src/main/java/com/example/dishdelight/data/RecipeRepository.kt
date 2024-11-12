
import com.example.dishdelight.data.Category
import com.example.dishdelight.data.Ingredient
import com.example.dishdelight.data.NetworkUtils
import com.example.dishdelight.data.Recipe
import com.example.dishdelight.data.RecipeDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class RecipeRepository {
    private val recipesByCategory = "https://www.themealdb.com/api/json/v1/1/filter.php?c="
    private val categoriesUrl = "https://www.themealdb.com/api/json/v1/1/categories.php"
    private val recipesDetailsById = "https://www.themealdb.com/api/json/v1/1/lookup.php?i="

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
                id = jsonObject.getString("idCategory"),
                title = jsonObject.getString("strCategory"),
                details = jsonObject.getString("strCategoryDescription"),
                imageUrl = jsonObject.getString("strCategoryThumb")
            )

            categories.add(category)
        }

        return categories
    }

    suspend fun getRecipes(recipeCategory: String): List<Recipe> {
        return withContext(Dispatchers.IO) {
            val response = NetworkUtils.get(recipesByCategory + recipeCategory)
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
                id = jsonObject.getString("idMeal"),
                title = jsonObject.getString("strMeal"),
                imageUrl = jsonObject.getString("strMealThumb")
            )
            recipes.add(recipe)
        }

        return recipes
    }

    suspend fun getRecipeDetails(recipeId: String): RecipeDetails {
        return withContext(Dispatchers.IO) {
            val response = NetworkUtils.get(recipesDetailsById + recipeId)
            parseRecipeDetails(response)
        }
    }

    private fun parseRecipeDetails(response: String): RecipeDetails {
        val jsonArray = JSONObject(response).getJSONArray("meals")

        val jsonObject = jsonArray.getJSONObject(0)
        val recipeDetails = RecipeDetails(
            id = jsonObject.getString("idMeal"),
            title = jsonObject.getString("strMeal"),
            category = jsonObject.getString("strCategory"),
            area = jsonObject.getString("strArea"),
            instructions = jsonObject.getString("strInstructions"),
            imageUrl = jsonObject.getString("strMealThumb"),
            youtubeUrl = jsonObject.getString("strYoutube"),
            recipeSourceUrl = jsonObject.getString("strSource"),
            tags = jsonObject.getString("strTags"),
            ingredients = parseIngredients(jsonObject))

        return recipeDetails
    }

    private fun parseIngredients(jsonObject: JSONObject): List<Ingredient> {
        var ingredients = mutableListOf<Ingredient>()
        for (i in 1 until 21) {
            var ingredientName = jsonObject.getString("strIngredient$i")
            if (ingredientName != "" && ingredientName != "null") {
                val ingredient = Ingredient(
                    name = ingredientName,
                    measure = jsonObject.getString("strMeasure$i")
                    //imageUrl = jsonObject.getString("https://www.themealdb.com/images/ingredients/${ingredientName.replace(" ", "%20")}-small.png")
                )
                ingredients.add(ingredient)
            }
        }

        return ingredients
    }
}