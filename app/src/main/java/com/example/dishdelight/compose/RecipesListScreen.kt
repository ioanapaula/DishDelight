package com.example.dishdelight.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
fun RecipeItem(recipe: Recipe, navController: NavController){
    Column(
        modifier = Modifier
        .clickable {
            val action = RecipeListFragmentDirections.actionRecipeListFragmentToRecipeDetailsFragment(recipe.id)
            navController.navigate(action)
        }
    ){
        AsyncImage(
            model = recipe.imageUrl,
            placeholder = painterResource(id = R.drawable.ic_file_placeholder),
            error = painterResource(id = R.drawable.ic_error),
            contentDescription = recipe.title,
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = recipe.title,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.CenterHorizontally)
                .width(150.dp)
        )
    }
}

@Composable
fun RecipesList(
    innerPadding: PaddingValues,
    navController: NavController,
    recipes: List<Recipe>
){
    Box(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(recipes) { recipe ->
                RecipeItem(recipe, navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipesListPreview() {
}