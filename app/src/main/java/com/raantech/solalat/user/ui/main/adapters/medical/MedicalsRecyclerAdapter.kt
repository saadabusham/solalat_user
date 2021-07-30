package com.raantech.solalat.user.ui.main.adapters.medical

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.models.medical.Medical
import com.raantech.solalat.user.databinding.RowMedicalServiceBinding
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.adapters.BaseViewHolder
import com.raantech.solalat.user.utils.extensions.setSlideAnimation

class MedicalsRecyclerAdapter(
    context: Context
) : BaseBindingRecyclerViewAdapter<Medical>(context) {

    private val viewBinderHelper = ViewBinderHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            RowMedicalServiceBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.setSlideAnimation(position)
        if (holder is ViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class ViewHolder(private val binding: RowMedicalServiceBinding) :
        BaseViewHolder<Medical>(binding.root) {

        override fun bind(item: Medical) {

            viewBinderHelper.setOpenOnlyOne(false)
            viewBinderHelper.bind(
                binding.swipeLayout,
                items[adapterPosition].id.toString()
            )
            binding.item = item

            binding.btnCallNow.setOnClickListener {
                itemClickListener?.onItemClick(it, adapterPosition, item)
            }
            binding.btnLocation.setOnClickListener {
                itemClickListener?.onItemClick(it, adapterPosition, item)
            }
            binding.imgFavorite.setOnClickListener {
                item.isWishlist = item.isWishlist == false
                notifyItemChanged(adapterPosition)
                itemClickListener?.onItemClick(it, adapterPosition, item)
            }
            binding.imgFavorite.setImageResource(
                if (item.isWishlist == true) {
                    R.drawable.ic_favorite_selected
                } else {
                    R.drawable.ic_favorite
                }
            )
        }
    }
}