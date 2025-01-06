package com.example.dishdelight.compose

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.example.dishdelight.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeFilterSection(
    title: String = "Filters",
    allFiltersButtonTitle: String = stringResource(id = R.string.view_all),
    filters: List<String>,
    filterCount: Int,
    filterClicked: (String) -> Unit,
    allFiltersClicked: () -> Unit,
    filtersPadding: Int = 8,
    padding: Int = 16,
    isExpandable: Boolean = false
) {
    var expandedState by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(padding.dp)) {
        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(6f)
            )
            TextButton(
                onClick = {
                    allFiltersClicked()
                    if (isExpandable) {
                        expandedState = !expandedState
                    }
                },
                modifier = Modifier
                    .weight(2f)
                    .padding(0.dp)
            ) {
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    text = if (expandedState) stringResource(id = R.string.view_all) else allFiltersButtonTitle
                )
            }
        }
        if (expandedState) {
            FlowRow(
                modifier = Modifier.padding(filtersPadding.dp),
                maxItemsInEachRow = 4
            ){
                filters.forEach { filter ->
                    SuggestionChip(
                        onClick = { filterClicked(filter) },
                        label = { Text(filter) },
                        modifier = Modifier.padding(filtersPadding.dp)
                    )
                }
            }
        } else {
            Row(
                Modifier.horizontalScroll(rememberScrollState())
            ) {
                for (i in 0 until filterCount) {
                    SuggestionChip(
                        onClick = { filterClicked(filters[i]) },
                        label = { Text(filters[i]) },
                        modifier = Modifier.padding(filtersPadding.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeFilterPreview() {
    HomeFilterSection(
        filters = listOf("Chicken", "Beef", "Pork", "Desert", "Seafood", "Vegan", "Starters"),
        filterCount = 3,
        filterClicked = {},
        allFiltersClicked = {},
        filtersPadding = 0
    )
}