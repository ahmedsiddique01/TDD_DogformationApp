package com.tdd.dogformation.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tdd.dogformation.R
import com.tdd.dogformation.databinding.ItemDogBinding
import com.tdd.dogformation.domain.model.DogBreed
import com.tdd.dogformation.utils.capitalizeFirstLetter
import com.tdd.dogformation.utils.loadUrl
import com.tdd.dogformation.utils.toCapitalizedWords

class DogListAdapter(private val onFavoriteClickListener: (DogBreed) -> Unit) :
    ListAdapter<DogBreed, DogListAdapter.DogBreedViewHolder>(DogBreedDiffCallback()) {

    class DogBreedViewHolder(val binding: ItemDogBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogBreedViewHolder {
        val binding = ItemDogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DogBreedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DogBreedViewHolder, position: Int) {
        val dogBreed = getItem(position)
        holder.binding.apply {

            dogNameTextView.text = dogBreed.name.capitalizeFirstLetter()

            subBreedsTextView.apply {
                text = if (dogBreed.subBreeds.isEmpty()) {
                    context.getString(R.string.sub_breeds_none)
                } else {
                    context.getString(R.string.sub_breeds, dogBreed.subBreeds.toCapitalizedWords())
                }
            }

            dogImageView.loadUrl(dogBreed.imageUrl)

            favoriteImageView.setImageResource(
                if (dogBreed.isFavourite) {
                    R.drawable.baseline_favorite_24
                } else {
                    R.drawable.baseline_favorite_border_24
                }
            )

            favoriteImageView.setOnClickListener {
                onFavoriteClickListener(dogBreed)
            }
        }
    }

    class DogBreedDiffCallback : DiffUtil.ItemCallback<DogBreed>() {
        override fun areItemsTheSame(oldItem: DogBreed, newItem: DogBreed): Boolean {
            return oldItem.name == newItem.name // Assuming name is unique
        }

        override fun areContentsTheSame(oldItem: DogBreed, newItem: DogBreed): Boolean {
            return oldItem == newItem
        }
    }
}