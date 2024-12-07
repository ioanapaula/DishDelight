package com.example.dishdelight.compose

import android.content.Intent
import android.net.Uri
import android.provider.Settings.Global.getString
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import com.example.dishdelight.R
import com.example.dishdelight.data.Ingredient
import com.example.dishdelight.data.RecipeDetails
import com.example.dishdelight.data.RecipeDetailsViewModel
import com.example.explorecompose.ExpandableCard

@Composable
fun RecipeDetailsScreen(
     viewModel: RecipeDetailsViewModel
) {
    val recipeDetails by viewModel.recipeDetails.observeAsState()

    if (recipeDetails != null){
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            RecipeDetails(innerPadding = innerPadding, recipeDetails = recipeDetails!!)
        }
    }
}

@Composable
fun RecipeDetails(
    innerPadding: PaddingValues,
    recipeDetails: RecipeDetails){

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { /* Optional: Handle the result if needed */ }

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

    val tagsString = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(stringResource(id = R.string.recipe_details_tags_bold))
        }
        append(" ")
        append(recipeDetails.tags)
    }

    val domainString = buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)) { // Apply font sizefor source string
            append(stringResource(id = R.string.recipe_details_source_bold))
        }
        withStyle(style = SpanStyle(fontSize = 12.sp)) { // Apply font size for domain string
            append(extractDomain(recipeDetails.recipeSourceUrl))
        }
    }
    Box(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 85.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box(modifier = Modifier) {
                AsyncImage(
                    model = recipeDetails.imageUrl,
                    contentDescription = "Recipe Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )
                if (recipeDetails.youtubeUrl.isNotEmpty() && recipeDetails.youtubeUrl != "null") {
                    Button(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(8.dp),
                        onClick = {
                            val url = recipeDetails.youtubeUrl
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            launcher.launch(intent)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red, // Change background color
                            contentColor = Color.White // Change text color
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.recipe_video_button),
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = recipeDetails.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.padding(0.dp, 4.dp),
                    text = categoryString
                )
                Text(
                    modifier = Modifier.padding(0.dp, 4.dp),
                    text = areaString
                )
                if (recipeDetails.tags.isNotEmpty() && recipeDetails.tags != "null") {
                    Text(
                        modifier = Modifier.padding(0.dp, 4.dp),
                        text = tagsString
                    )
                }
                Text(
                    modifier = Modifier.padding(0.dp, 8.dp),
                    text = stringResource(id = R.string.recipe_details_ingredients_title),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Column {
                    recipeDetails.ingredients.chunked(2).forEach{
                        rowItems ->
                            Row(modifier = Modifier.fillMaxWidth()) {
                                rowItems.forEach { ingredient ->
                                    Text(
                                        modifier = Modifier
                                            .padding(0.dp, 4.dp)
                                            .weight(1f),
                                        text = "${ingredient.measure} ${ingredient.name}"
                                    )
                                }
                            }
                    }
                }
                ExpandableCard(
                    title = stringResource(
                        id = R.string.recipe_details_instructions_title,
                        recipeDetails.title
                    ),
                    titleStyle = MaterialTheme.typography.titleMedium,
                    text = recipeDetails.instructions,
                    cardPadding = 0.dp
                )
            }
        }
        if (recipeDetails.recipeSourceUrl.isNotEmpty() && recipeDetails.recipeSourceUrl != "null"){
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(8.dp, 24.dp, 8.dp, 16.dp),
                onClick = {
                    val url = recipeDetails.recipeSourceUrl
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    launcher.launch(intent)
                }) {
                Text(
                    textAlign = TextAlign.Center,
                    text = domainString
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailsPreview() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        RecipeDetails(
            innerPadding = innerPadding,
            recipeDetails = RecipeDetails(
                title = "Chicken Teriyaki Casserole",
                category = "Chicken",
                area = "Japanese",
                tags = "Chicken, Soy sauce",
                id = "2345",
                youtubeUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
                recipeSourceUrl = "https://www.google.com",
                imageUrl = "some url",
                instructions = "Some instructions to display in the preview",
                ingredients = listOf(Ingredient("Chicken", "1 lb"), Ingredient("Soy sauce", "1/2 cup"))))
    }
}

private fun extractDomain(url: String): String {
    val regex = Regex("https?://(www\\.)?([a-zA-Z0-9.-]+).*")
    val matchResult = regex.find(url)
    return matchResult?.groups?.get(2)?.value ?: url
}
