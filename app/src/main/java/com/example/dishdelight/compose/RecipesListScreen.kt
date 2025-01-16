package com.example.dishdelight.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import coil.compose.AsyncImage
import com.example.dishdelight.R
import com.example.dishdelight.data.Recipe
import com.example.dishdelight.ui.fragments.RecipeListFragmentDirections
import com.example.dishdelight.viewmodels.RecipeListViewModel

@Composable
fun RecipesListScreen(
    viewModel: RecipeListViewModel,
    navController: NavController
){
    val recipes = viewModel.recipes.observeAsState(emptyList())

    if (recipes != null) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            RecipesList(
                innerPadding = innerPadding,
                navController = navController,
                recipes = recipes.value)
        }
    }
}

@Composable
fun RecipeItem(recipe: Recipe, onClick: () -> Unit){
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        onClick = onClick){
        AsyncImage(
            model = recipe.imageUrl+ "/preview",
            placeholder = painterResource(id = R.drawable.ic_file_placeholder),
            error = painterResource(id = R.drawable.ic_error),
            contentDescription = recipe.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(130.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = recipe.title,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 4.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun RecipesList(
    innerPadding: PaddingValues,
    navController: NavController,
    recipes: List<Recipe>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .imePadding(),
        contentPadding = PaddingValues(
            horizontal = 12.dp,
            vertical = 24.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(recipes) { recipe ->
            RecipeItem(recipe, onClick = {
                val action = RecipeListFragmentDirections.actionRecipeListFragmentToRecipeDetailsFragment(recipe.id)
                navController.navigate(action)
            })
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RecipesListPreview() {
}