package com.raantech.solalat.user.ui.wishlist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raantech.solalat.user.data.models.wishlist.WishList
import com.raantech.solalat.user.databinding.RowWishlistBinding
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.adapters.BaseViewHolder

class WishListRecyclerAdapter(
    context: Context
) : BaseBindingRecyclerViewAdapter<WishList>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            RowWishlistBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class ViewHolder(private val binding: RowWishlistBinding) :
        BaseViewHolder<WishList>(binding.root) {

        override fun bind(item: WishList) {
            binding.item = item
            binding.imgFavorite.setOnClickListener {
                itemClickListener?.onItemClick(it, adapterPosition, item)
            }
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(it, adapterPosition, item)
            }
        }
    }
}