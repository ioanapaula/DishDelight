package com.example.dishdelight.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.dishdelight.compose.HomeScreen
import com.example.dishdelight.viewmodels.HomeScreenViewModel

class HomeScreenFragment : Fragment() {
    private lateinit var viewModel: HomeScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[HomeScreenViewModel::class.java]
        viewModel.run { fetchRandomRecipe() }

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val navController = findNavController()
                HomeScreen(viewModel, navController)
            }
        }
    }
}