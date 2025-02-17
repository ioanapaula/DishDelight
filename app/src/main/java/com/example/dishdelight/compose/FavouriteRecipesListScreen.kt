package com.example.dishdelight.compose

import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import coil.compose.AsyncImage
import com.example.dishdelight.R
import com.example.dishdelight.room.FavouriteRecipe
import com.example.dishdelight.ui.fragments.FavouriteRecipesListFragmentDirections
import com.example.dishdelight.viewmodels.FavouriteRecipesListViewModel

@Composable
fun FavouriteRecipesListScreen(
    viewModel: FavouriteRecipesListViewModel,
    navController: NavController
) {
    val recipes = viewModel.recipes.observeAsState(emptyList())
    if (recipes != null) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            FavouriteRecipesList(
                innerPadding = innerPadding,
                viewModel = viewModel,
                navController = navController,
                recipes = recipes.value
            )
        }
    }
}

@Composable
fun FavouriteRecipesList(
    innerPadding: PaddingValues,
    viewModel: FavouriteRecipesListViewModel,
    navController: NavController,
    recipes: List<FavouriteRecipe> = emptyList()) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(recipes) { recipe ->
            FavouriteRecipeItem(recipe,
                onClick =
                {
                    val action =FavouriteRecipesListFragmentDirections.actionFavouriteRecipesFragmentToRecipeDetailsFragment(recipe.recipeId.toString())
                    navController.navigate(action)
                },
                onDelete = { recipeId -> viewModel.removeFromFavourites(recipeId) })
        }
    }
}

@Composable
fun FavouriteRecipeItem(recipe: FavouriteRecipe, onClick: () -> Unit, onDelete: (Int) -> Unit) {
    ElevatedCard(
        colors = CardColors(containerColor = Color.White, contentColor = Color.Transparent, disabledContentColor = Color.Transparent, disabledContainerColor = Color.Transparent),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .background(color = Color.White)
            .padding(horizontal = 12.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = recipe.recipeImageUrl,
                placeholder = painterResource(id = R.drawable.ic_file_placeholder),
                error = painterResource(id = R.drawable.ic_error),
                contentDescription = recipe.recipeTitle,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .width(128.dp)
                    .height(128.dp)
            )
            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(0.8f))
            {
                Text(
                    text = recipe.recipeTitle,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    text = recipe.recipeCategory + " | " + recipe.recipeArea,
                    color = Color.Black,
                    fontSize = 14.sp,
                )
            }
            Spacer(modifier = Modifier.weight(0.1f))
            IconButton(
                onClick = { onDelete(recipe.recipeId) }) {
                Image(
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp),
                    painter = painterResource(id = R.drawable.vector_delete_icon),
                    contentDescription = "delete_button"
                )
            }
        }
    }
}

//@Preview
//@Composable
//fun FavouriteRecipesListPreview() {
//    Column {
//        FavouriteRecipeItem(
//            recipe = FavouriteRecipe(
//                recipeTitle = "Tiramisu",
//                recipeCategory = "Desert",
//                recipeArea = "Italian",
//                recipeTags = "",
//                recipeNotes = "",
//                recipeImageUrl = "Image URL",
//                recipeId = 4331, )
//        ) {
//        }
//    }
//}

