package com.raantech.solalat.user.ui.horse.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raantech.solalat.user.databinding.RowPriceDigitBinding
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.adapters.BaseViewHolder

class PriceDigitsRecyclerAdapter(
    context: Context
) : BaseBindingRecyclerViewAdapter<Int>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImageViewHolder(
            RowPriceDigitBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ImageViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class ImageViewHolder(private val binding: RowPriceDigitBinding) :
        BaseViewHolder<Int>(binding.root) {

        override fun bind(item: Int) {
            binding.item = item
            binding.position = adapterPosition
            binding.imgUp.setOnClickListener {
                itemClickListener?.onItemClick(it, adapterPosition, item)
            }
            binding.imgDown.setOnClickListener {
                itemClickListener?.onItemClick(it, adapterPosition, item)
            }
        }
    }

}