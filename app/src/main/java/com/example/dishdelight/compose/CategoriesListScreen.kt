package com.example.dishdelight.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import coil.compose.AsyncImage
import com.example.dishdelight.R
import com.example.dishdelight.data.Category
import com.example.dishdelight.ui.fragments.CategoryListFragmentDirections
import com.example.dishdelight.viewmodels.CategoryListViewModel

@Composable
fun CategoriesListScreen(
    viewModel: CategoryListViewModel,
    navController: NavController
) {
    val categories = viewModel.categories.observeAsState(emptyList())

    if (categories != null) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            CategoriesList(
                innerPadding = innerPadding,
                navController = navController,
                categories = categories.value)
        }
    }
}

@Composable
fun CategoryItem(category: Category, navController: NavController) {
    Card(
        onClick = {
            val action = CategoryListFragmentDirections.actionCategoryListFragmentToRecipeListFragment(category.title)
            navController.navigate(action)
        },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = category.imageUrl,
                placeholder = painterResource(id = R.drawable.ic_file_placeholder),
                error = painterResource(id = R.drawable.ic_error),
                contentDescription = category.title,
                modifier = Modifier.size(100.dp)
            )
            Column(
                modifier = Modifier.padding(start = 12.dp, end = 8.dp)
            ) {
                Text(
                    text = category.title ?: "",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp))
                Text(
                    text = category.details ?: "",
                    color = Color.Black,
                    fontSize = 12.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun CategoriesList(
    innerPadding: PaddingValues,
    navController: NavController,
    categories: List<Category>
    ) {
    Box(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories) { category ->
                CategoryItem(category, navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesListPreview() {
}