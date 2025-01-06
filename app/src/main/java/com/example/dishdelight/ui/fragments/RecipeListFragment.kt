package com.example.dishdelight.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dishdelight.compose.RecipesListScreen
import com.example.dishdelight.viewmodels.RecipeListViewModel

class RecipeListFragment : Fragment() {
    private lateinit var viewModel: RecipeListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args: RecipeListFragmentArgs by navArgs()
        val filterValue = args.filterValue
        val filterType = args.filterType

        viewModel = ViewModelProvider(this)[RecipeListViewModel::class.java]
        viewModel.run {
            if (filterType == "category") {
                fetchRecipes(category = filterValue)
            }
            else{
                fetchRecipes(area = filterValue)
            }
        }

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val navController = findNavController()
                RecipesListScreen(viewModel, navController)
            }
        }
    }
}