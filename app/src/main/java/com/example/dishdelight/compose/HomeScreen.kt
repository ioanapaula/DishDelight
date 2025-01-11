package com.example.dishdelight.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import androidx.navigation.NavDirections
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
    val categoryNames = viewModel.categoryList.observeAsState().value?.map{ it.title ?: "" } ?: emptyList()
    val areaNames = viewModel.areaList.observeAsState().value?.map{ it.title ?: "" } ?: emptyList()
    val categoriesCount = 5
    val areasCount = 5

    if (recipeDetails != null) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Text(
                modifier = Modifier.padding(top = 32.dp, bottom = 8.dp, start = 16.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                text = stringResource(R.string.home_header_title)
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyLarge,
                text = stringResource(R.string.home_header_message)
            )
            HomeRandomRecipe(recipeDetails!!, recipeDetailsClicked = {
                val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToRecipeDetailsFragment(recipeDetails.id)
                navController.navigate(action)
            })
            HomeFilterSection(
                title = stringResource(id = R.string.categories_list_title),
                allFiltersButtonTitle = stringResource(id = R.string.view_all),
                filters = categoryNames,
                filterCount = categoriesCount,
                filterClicked = { filterValue ->
                    val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToRecipeListFragment(filterType = "category", filterValue)
                    navController.navigate(action)
                },
                allFiltersClicked = {
                    val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToCategoryListFragment()
                    navController.navigate(action)
                }
            )
            HomeFilterSection(
                title = stringResource(id = R.string.areas_list_title),
                allFiltersButtonTitle = stringResource(id = R.string.view_all),
                filters = areaNames,
                filterCount = areasCount,
                filterClicked = { filterValue ->
                    val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToRecipeListFragment(filterType = "area", filterValue)
                    navController.navigate(action)
                },
                allFiltersClicked = {
                },
                isExpandable = true
            )
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

    Box(
        modifier = Modifier
            .padding(16.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = recipeDetails.imageUrl,
                placeholder = painterResource(id = R.drawable.ic_file_placeholder),
                error = painterResource(id = R.drawable.ic_error),
                contentDescription = "Recipe Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                text = recipeDetails.title
            )
            Row {
                Column(modifier = Modifier.weight(6f)) {
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        text = categoryString
                    )
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        text = areaString
                    )
                }
                TextButton(
                    onClick = recipeDetailsClicked,
                    modifier = Modifier.weight(3f)
                ) {
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        text = "Learn more"
                    )
                }
            }
        }
    }
}

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
        HomeFilterSection(filters = listOf("Chicken", "Beef", "Pork", "Desert", "Seafood", "Vegan", "Starters"), filterCount = 5, filterClicked = {}, allFiltersClicked = {})
    }
}