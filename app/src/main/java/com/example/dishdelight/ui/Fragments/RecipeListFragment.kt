package com.example.dishdelight.ui.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dishdelight.R
import com.example.dishdelight.data.RecipeListViewModel
import com.example.dishdelight.databinding.FragmentRecipeListBinding
import com.example.dishdelight.ui.Adapters.RecipeAdapter

class RecipeListFragment : Fragment() {
    private lateinit var viewModel: RecipeListViewModel
    private lateinit var adapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRecipeListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_recipe_list, container, false
        )
        val args: RecipeListFragmentArgs by navArgs()
        val categoryName = args.categoryName

        viewModel = ViewModelProvider(this)[RecipeListViewModel::class.java]
        // temporary way to pass the category name to the ViewModel
        viewModel.run { fetchRecipes(categoryName) }
        binding.recipeListViewModel = viewModel
        binding.lifecycleOwner = this

        adapter = RecipeAdapter{ recipe ->
            val action = RecipeListFragmentDirections.actionRecipeListFragmentToRecipeDetailsFragment(recipe.id)
            findNavController().navigate(action)}
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            recipes?.let {
                adapter.submitList(it)
            }
        }

        return binding.root
    }
}