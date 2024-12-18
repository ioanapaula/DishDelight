package com.example.dishdelight.ui.fragments

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dishdelight.R
import com.example.dishdelight.viewmodels.CategoryListViewModel
import com.example.dishdelight.databinding.FragmentCategoryListBinding
import com.example.dishdelight.ui.adapters.CategoryAdapter

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

        adapter = CategoryAdapter { category ->
            val action = CategoryListFragmentDirections.actionCategoryListFragmentToRecipeListFragment(category.title)
            findNavController().navigate(action)
        }
        val insetDecoration = VerticalItemDecoration(12)

        binding.recyclerView.addItemDecoration(insetDecoration)
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

class VerticalItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(0, spacing, 0, spacing)
    }
}