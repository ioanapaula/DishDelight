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
import com.example.dishdelight.R
import com.example.dishdelight.data.RecipeDetails

class RecipeDetailsFragment: Fragment() {
    private lateinit var viewModel: RecipeDetailsViewModel
    private lateinit var binding: FragmentRecipeDetailsBinding
    private lateinit var ingredientAdapter: IngredientAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)
        val args: RecipeDetailsFragmentArgs by navArgs()
        val recipeId = args.recipeId

        viewModel = ViewModelProvider(this)[RecipeDetailsViewModel::class.java]

        // temporary way to pass the recipe id to the ViewModel
        viewModel.run { fetchRecipeDetails(recipeId) }
        binding.recipeDetailsViewModel = viewModel
        binding.lifecycleOwner = this

        val insetDecoration = VerticalItemDecoration(8)
        binding.ingredientsRecyclerView.addItemDecoration(insetDecoration)

        setBindings()

        return binding.root
    }

    private fun setBindings() {
        viewModel.recipeDetails.observe(viewLifecycleOwner) { details ->
            binding.recipeDetails = details // Set the data to the binding object

            val imageView = binding.recipeImage
            imageView.load(details.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_file_placeholder)
                error(R.drawable.ic_error)
            }

            ingredientAdapter = IngredientAdapter(details.ingredients)
            binding.ingredientsRecyclerView.adapter = ingredientAdapter
            binding.ingredientsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

            binding.recipeCategory.text = getString(R.string.recipe_details_category, details.category)
            binding.recipeCategory.boldSubstring("Category:")

            binding.recipeArea.text = getString(R.string.recipe_details_area, details.area)
            binding.recipeArea.boldSubstring("Area:")

            binding.recipeTags.text = getString(R.string.recipe_details_tags, details.tags)
            binding.recipeTags.boldSubstring("Keywords:")

            binding.recipeTags.visibility = if (details.tags.isNotEmpty() && details.tags != "null") View.VISIBLE else View.GONE
            binding.recipeFullDetailsButton.visibility = if (details.recipeSourceUrl.isNotEmpty() && details.recipeSourceUrl != "null") View.VISIBLE else View.GONE

            val recipeDomain = extractDomain(details.recipeSourceUrl)
            binding.recipeFullDetailsButton.text = getString(R.string.recipe_details_source, recipeDomain)
            binding.recipeFullDetailsButton.sizeSubstring(recipeDomain, 12)
            binding.recipeFullDetailsButton.setOnClickListener {
                val url = details.recipeSourceUrl
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }

            binding.recipeVideoButton.setOnClickListener {
                val url = details.youtubeUrl
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }
    }

    private fun extractDomain(url: String): String {
        val regex = Regex("https?://(www\\.)?([a-zA-Z0-9.-]+).*")
        val matchResult = regex.find(url)
        return matchResult?.groups?.get(2)?.value ?: url
    }

    fun TextView.boldSubstring(substring: String) {
        val text = this.text.toString()
        val spannableString = SpannableString(text)
        val startIndex = text.indexOf(substring)
        val endIndex = startIndex + substring.length
        val styleSpan = StyleSpan(Typeface.BOLD)
        spannableString.setSpan(styleSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        this.text = spannableString
    }

    fun TextView.sizeSubstring(substring: String, sizeSp: Int) {
        val text = this.text.toString()
        val spannableString = SpannableString(text)
        val startIndex = text.indexOf(substring)
        val endIndex = startIndex + substring.length
        val sizeSpan = AbsoluteSizeSpan(sizeSp, true)
        spannableString.setSpan(sizeSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        this.text = spannableString
    }
}