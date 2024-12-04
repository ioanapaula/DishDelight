package com.example.dishdelight.ui.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.example.dishdelight.data.RecipeDetailsViewModel
import com.example.dishdelight.databinding.FragmentRecipeDetailsBinding
import com.example.dishdelight.ui.Adapters.IngredientAdapter
import android.graphics.Typeface
import android.text.style.AbsoluteSizeSpan
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.example.dishdelight.R
import com.example.dishdelight.compose.RecipeDetailsScreen
import com.example.dishdelight.data.RecipeDetails

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

    private fun extractDomain(url: String): String {
        val regex = Regex("https?://(www\\.)?([a-zA-Z0-9.-]+).*")
        val matchResult = regex.find(url)
        return matchResult?.groups?.get(2)?.value ?: url
    }
}