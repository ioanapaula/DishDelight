package com.example.dishdelight.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishdelight.R
import com.example.dishdelight.data.RecipeListViewModel
import com.example.dishdelight.databinding.FragmentRecipeListBinding

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

        adapter = RecipeAdapter()
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