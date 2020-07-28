package com.kotlinkrew.stateflowexample.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kotlinkrew.stateflowexample.R
import kotlinx.android.synthetic.main.item_breed_image.view.*

class ImageAdapter : ListAdapter<String, ImageAdapter.ImageViewHolder>(ImageDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_breed_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ImageViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bind(imageUrl: String) {
            Glide.with(itemView.context).load(imageUrl).centerCrop().into(itemView.breed_image)
        }
    }

    class ImageDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}