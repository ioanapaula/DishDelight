package com.example.dishdelight.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.dishdelight.compose.RecipeDetailsScreen
import com.example.dishdelight.viewmodels.RecipeDetailsViewModel

class RecipeDetailsFragment: Fragment() {
    private lateinit var viewModel: RecipeDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args: RecipeDetailsFragmentArgs by navArgs()
        val recipeId = args.recipeId

        viewModel = ViewModelProvider(this)[RecipeDetailsViewModel::class.java]
        viewModel.run { fetchRecipeDetails(recipeId) }

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                RecipeDetailsScreen(viewModel)
            }
        }
    }
}