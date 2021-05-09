package com.raantech.solalat.user.ui.media.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raantech.solalat.user.data.models.media.Media
import com.raantech.solalat.user.databinding.RowImageViewBinding
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.adapters.BaseViewHolder

class MediaRecyclerAdapter(
        context: Context
) : BaseBindingRecyclerViewAdapter<Media>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImageViewHolder(
                RowImageViewBinding.inflate(
                        LayoutInflater.from(context), parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ImageViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class ImageViewHolder(private val binding: RowImageViewBinding) :
            BaseViewHolder<Media>(binding.root) {

        override fun bind(item: Media) {
            binding.item = item
            binding.imgRemove.setOnClickListener {
                itemClickListener?.onItemClick(it, adapterPosition, item)
            }
            binding.relativePreview.setOnClickListener {
                itemClickListener?.onItemClick(it, adapterPosition, item)
            }
            binding.relativePreview.setOnLongClickListener {
                itemClickListener?.onItemLongClick(it, adapterPosition, item)
                return@setOnLongClickListener true
            }
        }
    }

}