package com.example.dishdelight.ui.fragments;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.example.dishdelight.viewmodels.FavouriteRecipesListViewModel
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.dishdelight.compose.FavouriteRecipesListScreen

class FavouriteRecipesListFragment : Fragment() {
    private lateinit var viewModel: FavouriteRecipesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this)[FavouriteRecipesListViewModel::class.java]
        viewModel.run {
            fetchFavouriteRecipes()
        }

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val navController = findNavController()
                FavouriteRecipesListScreen(viewModel, navController)
            }
        }
    }
}
