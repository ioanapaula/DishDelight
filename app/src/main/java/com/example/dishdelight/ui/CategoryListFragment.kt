package com.example.dishdelight.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishdelight.R
import com.example.dishdelight.data.CategoryListViewModel
import com.example.dishdelight.databinding.FragmentCategoryListBinding

class CategoryListFragment : Fragment() {
    private lateinit var viewModel: CategoryListViewModel
    private lateinit var adapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCategoryListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_category_list, container, false
        )
        viewModel = ViewModelProvider(this)[CategoryListViewModel::class.java]
        binding.categoryListViewModel = viewModel
        binding.lifecycleOwner = this

        adapter = CategoryAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            categories?.let {
                adapter.submitList(it)
            }
        }

        return binding.root
    }

}