package com.raantech.solalat.user.ui.main.adapters.barn

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raantech.solalat.user.data.models.media.Media
import com.raantech.solalat.user.databinding.RowSliderBinding
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.adapters.BaseViewHolder

class SliderAdapter(
    context: Context
) :
    BaseBindingRecyclerViewAdapter<Media>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
                RowSliderBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class ViewHolder(private val binding: RowSliderBinding) :
        BaseViewHolder<Media>(binding.root) {

        override fun bind(item: Media) {
            binding.data = item
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(it,adapterPosition,item)
            }
        }
    }


}