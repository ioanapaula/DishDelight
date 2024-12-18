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
import com.example.dishdelight.compose.CategoriesListScreen
import com.example.dishdelight.viewmodels.CategoryListViewModel

class CategoryListFragment : Fragment() {
    private lateinit var viewModel: CategoryListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this)[CategoryListViewModel::class.java]
        viewModel.run { fetchCategories() }

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val navController = findNavController()
                CategoriesListScreen(viewModel, navController)
            }
        }
    }
}