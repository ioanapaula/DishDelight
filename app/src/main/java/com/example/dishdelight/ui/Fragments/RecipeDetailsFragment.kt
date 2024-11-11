package com.example.dishdelight.ui.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.example.dishdelight.data.RecipeDetailsViewModel
import com.example.dishdelight.databinding.FragmentRecipeDetailsBinding
import com.example.dishdelight.ui.Adapters.IngredientAdapter

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
                //placeholder(R.drawable.placeholder_image)
                //error(R.drawable.error_image)
            }

            ingredientAdapter = IngredientAdapter(details.ingredients)
            binding.ingredientsRecyclerView.adapter = ingredientAdapter
            binding.ingredientsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }
}