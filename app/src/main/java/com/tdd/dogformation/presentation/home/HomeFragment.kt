package com.tdd.dogformation.presentation.home

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.tdd.dogformation.databinding.FragmentHomeBinding
import com.tdd.dogformation.presentation.adapters.DogListAdapter
import com.tdd.dogformation.presentation.base.BaseFragment
import com.tdd.dogformation.utils.hide
import com.tdd.dogformation.utils.show
import com.tdd.dogformation.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var dogListAdapter: DogListAdapter

    override fun setupView() {
        initViews()
        initFlow()
    }

    private fun initViews() {
        initRecyclerView()
        initSearchListener()
        initFab()
    }

    private fun initFab() {
        binding.favoritesActionButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFavoritesFragment())
        }
    }

    private fun initSearchListener() {
        binding.apply {
            searchTextInputLayout.editText?.doAfterTextChanged { editable ->
                viewModel.apply {
                    query = editable?.toString()?.trim() ?: ""
                    getDogBreedsList()
                }
            }
        }
    }

    private fun initFlow() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    if (uiState.isLoading) {
                        showProgressbar()
                    } else {
                        hideProgressbar()
                        if (!uiState.errorMessage.isNullOrBlank()) {
                            context?.showToast(uiState.errorMessage)
                        } else {
                            uiState.dogBreeds.also {
                                if (it.isNullOrEmpty()) {
                                    showNoResult()
                                } else {
                                    hideNoResult()
                                }
                            }
                            dogListAdapter.submitList(uiState.dogBreeds)
                        }
                    }
                }
            }
        }
        viewModel.getDogBreedsList()
    }

    private fun showNoResult() {
        binding.noResultsTextView.show()
    }

    private fun hideNoResult() {
        binding.noResultsTextView.hide()
    }

    private fun hideProgressbar() {
        binding.progressBar.hide()
    }

    private fun showProgressbar() {
        binding.progressBar.show()
    }

    private fun initRecyclerView() {

        dogListAdapter = DogListAdapter { dogBreed ->
            viewModel.makeFavoriteAndGetList(
                binding.searchTextInputLayout.editText?.text?.toString()?.trim() ?: "",
                dogBreed.name,
                !dogBreed.isFavourite
            )
        }

        binding.recyclerViewCat.apply {
            adapter = dogListAdapter
        }
    }
}