package com.example.dishdelight.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

        viewModel = ViewModelProvider(this)[RecipeListViewModel::class.java]
        binding.recipeListViewModel = viewModel
        binding.lifecycleOwner = this

        adapter = RecipeAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter.submitList(viewModel.recipes)

        return binding.root
    }
}