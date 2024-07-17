package com.tdd.dogformation.presentation.favorites

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.tdd.dogformation.databinding.FragmentFavoritesBinding
import com.tdd.dogformation.presentation.adapters.DogListAdapter
import com.tdd.dogformation.presentation.base.BaseFragment
import com.tdd.dogformation.utils.hide
import com.tdd.dogformation.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>() {

    private lateinit var dogListAdapter: DogListAdapter
    private val viewModel: FavoritesViewModel by viewModels()
    override fun setupView() {
        initBack()
        initRecyclerView()
        initFlow()
    }

    private fun initRecyclerView() {

        dogListAdapter = DogListAdapter {
            viewModel.removeFromFavorites(it)
        }

        binding.dogsRecyclerView.apply {
            adapter = dogListAdapter
        }
    }

    private fun initBack() {
        binding.backImageView.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initFlow() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteDogBreeds.collect { dogBreeds ->
                    dogListAdapter.submitList(dogBreeds)
                    binding.noResultsTextView.apply {
                        if (dogBreeds.isEmpty()) show() else hide()
                    }
                }
            }
        }
    }
}