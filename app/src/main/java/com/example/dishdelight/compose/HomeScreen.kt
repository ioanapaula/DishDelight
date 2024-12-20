package com.example.dishdelight.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.dishdelight.R
import com.example.dishdelight.data.Ingredient
import com.example.dishdelight.data.RecipeDetails
import com.example.dishdelight.ui.fragments.HomeScreenFragmentDirections
import com.example.dishdelight.viewmodels.HomeScreenViewModel

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    navController: NavController
) {
    val recipeDetails = viewModel.recipeDetails.observeAsState().value

    if (recipeDetails != null) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())) {
            HomeRandomRecipe(recipeDetails!!, recipeDetailsClicked = {
                val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToRecipeDetailsFragment(recipeDetails.id)
                navController.navigate(action)
            })
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 24.dp, 8.dp, 16.dp),
                onClick = {
                    val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToCategoryListFragment()
                    navController.navigate(action)
                }) {
                Text(text = "Categories")
            }
        }
    }
}

@Composable
fun HomeRandomRecipe(
    recipeDetails: RecipeDetails,
    recipeDetailsClicked: () -> Unit
) {
    val categoryString = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(stringResource(id = R.string.recipe_details_category_bold))
        }
        append(" ")
        append(recipeDetails.category)
    }

    val areaString = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(stringResource(id = R.string.recipe_details_area_bold))
        }
        append(" ")
        append(recipeDetails.area)
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            text = "Feeling lucky?")
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.bodyMedium,
            text = "Try out this random recipe and spice up your repertoire")
        AsyncImage(
            model = recipeDetails.imageUrl,
            placeholder = painterResource(id = R.drawable.ic_file_placeholder),
            error = painterResource(id = R.drawable.ic_error),
            contentDescription = "Recipe Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop)
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            text = recipeDetails.title)
        Row {
            Column(modifier = Modifier.weight(6f)) {
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = categoryString)
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = areaString)
            }
            TextButton(
                onClick = recipeDetailsClicked,
                modifier = Modifier.weight(3f)
            ) {
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    text = "Learn more")
            }
        }
    }
}

@Composable
fun HomeRandomCategories(
) {

}

//@Composable
//fun HomeRandomCategories(
//) {
//
//}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Column {
        HomeRandomRecipe(recipeDetails = RecipeDetails(
            title = "Chicken Teriyaki Casserole",
            category = "Chicken",
            area = "Japanese",
            tags = "Chicken, Soy sauce",
            id = "2345",
            youtubeUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
            recipeSourceUrl = "",
            imageUrl = "some url",
            instructions = "Some instructions to display in the previewSome instructions to display in the previewSome instructions to display in the previewSome instructions to display in the previewSome instructions to display in the previewSome instructions to display in the previewSome instructions to display in the preview",
            ingredients = listOf(Ingredient("Chicken", "1 lb"), Ingredient("Soy sauce", "1/2 cup"))),
            recipeDetailsClicked = {})
    }
}