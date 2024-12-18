package com.example.dishdelight.compose

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.dishdelight.R
import com.example.dishdelight.data.Ingredient
import com.example.dishdelight.data.RecipeDetails
import com.example.dishdelight.viewmodels.RecipeDetailsViewModel
import com.example.explorecompose.ExpandableCard

@Composable
fun RecipeDetailsScreen(
     viewModel: RecipeDetailsViewModel
) {
    val recipeDetails = viewModel.recipeDetails.observeAsState().value
    val hasSourceUrl = viewModel.hasSourceUrl.observeAsState(initial = false).value
    val hasYoutubeUrl = viewModel.hasYoutubeUrl.observeAsState(initial = false).value

    if (recipeDetails != null){
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            RecipeDetails(
                innerPadding = innerPadding,
                recipeDetails = recipeDetails!!,
                hasSourceUrl = hasSourceUrl,
                hasYoutubeUrl = hasYoutubeUrl)
        }
    }
}

@Composable
fun RecipeDetails(
    innerPadding: PaddingValues,
    recipeDetails: RecipeDetails,
    hasSourceUrl: Boolean = false,
    hasYoutubeUrl: Boolean = false) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { /* Optional: Handle the result if needed */ }

    // region Text formatting
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
        withStyle(
            style = SpanStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        ) {
            append(stringResource(id = R.string.recipe_details_source_bold))
        }
        withStyle(style = SpanStyle(fontSize = 12.sp)) { // Apply font size for domain string
            append(extractDomain(recipeDetails.recipeSourceUrl ?: ""))
        }
    }
    // endregion

    Box(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = if (hasSourceUrl) 85.dp else 10.dp)
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
                if (hasYoutubeUrl) {
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
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier
                            .weight(8f)
                            .align(Alignment.CenterVertically),
                        text = recipeDetails.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    if (hasYoutubeUrl || hasSourceUrl) {
                        IconButton(
                            modifier = Modifier
                                .weight(1f),
                            onClick = {
                                var recipeSharedContent =
                                    if (hasSourceUrl) {
                                        recipeDetails.recipeSourceUrl
                                    } else {
                                        recipeDetails.youtubeUrl
                                    }
                                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(
                                        Intent.EXTRA_TEXT,
                                        "Check out this recipe: ${recipeDetails.title} - ${recipeSharedContent}"
                                    )
                                }
                                launcher.launch(sendIntent)
                            }) {
                            Image(
                                painter = painterResource(id = R.drawable.vector_share_icon),
                                contentDescription = "Share button"
                            )
                        }
                    }
                }
                Text(
                    modifier = Modifier.padding(0.dp, 4.dp),
                    text = categoryString
                )
                Text(
                    modifier = Modifier.padding(0.dp, 4.dp),
                    text = areaString
                )
                if (!recipeDetails.tags.isNullOrEmpty()) {
                    Text(
                        modifier = Modifier.padding(0.dp, 4.dp),
                        text = tagsString
                    )
                }
                Text(
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                    text = stringResource(id = R.string.recipe_details_ingredients_title),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                    recipeDetails.ingredients.chunked(2).forEach { rowItems ->
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
        if (hasSourceUrl) {
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
            hasSourceUrl = true,
            hasYoutubeUrl = true,
            recipeDetails = RecipeDetails(
                title = "Chicken Teriyaki Casserole",
                category = "Chicken",
                area = "Japanese",
                tags = "Chicken, Soy sauce",
                id = "2345",
                youtubeUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
                recipeSourceUrl = "",
                imageUrl = "some url",
                instructions = "Some instructions to display in the previewSome instructions to display in the previewSome instructions to display in the previewSome instructions to display in the previewSome instructions to display in the previewSome instructions to display in the previewSome instructions to display in the preview",
                ingredients = listOf(Ingredient("Chicken", "1 lb"), Ingredient("Soy sauce", "1/2 cup"))))
    }
}

private fun extractDomain(url: String): String {
    val regex = Regex("https?://(www\\.)?([a-zA-Z0-9.-]+).*")
    val matchResult = regex.find(url)
    return matchResult?.groups?.get(2)?.value ?: url
}
