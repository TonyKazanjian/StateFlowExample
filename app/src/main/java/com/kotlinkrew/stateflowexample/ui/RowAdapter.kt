package com.kotlinkrew.stateflowexample.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kotlinkrew.stateflowexample.R
import com.kotlinkrew.stateflowexample.domain.model.DogBreed
import kotlinx.android.synthetic.main.item_breed_row.view.*


class RowAdapter: ListAdapter<DogBreed, RowAdapter.RowViewHolder>(RowDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_breed_row, parent, false)
        return RowViewHolder(view)
    }

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCurrentListChanged(
        previousList: MutableList<DogBreed>,
        currentList: MutableList<DogBreed>
    ) {
        super.onCurrentListChanged(previousList, currentList)
    }

    inner class RowViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val imageAdapter = ImageAdapter()

        init {
            itemView.recycler_view_breed_images.apply {
                setHasFixedSize(true)
                setRecycledViewPool(RecyclerView.RecycledViewPool())
                layoutManager =
                    LinearLayoutManager(
                        itemView.context,
                        RecyclerView.HORIZONTAL,
                        false)
                adapter = imageAdapter
            }
        }

        fun bind(item: DogBreed){
            itemView.text_view_breed_name.text = item.name
            imageAdapter.submitList(item.images)
        }
    }

    class RowDiffCallback : DiffUtil.ItemCallback<DogBreed>() {
        override fun areItemsTheSame(oldItem: DogBreed, newItem: DogBreed): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: DogBreed, newItem: DogBreed): Boolean {
            return oldItem == newItem
        }

    }
}

