package com.raantech.solalat.user.ui.main.adapters.medical

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raantech.solalat.user.data.models.ServiceCategory
import com.raantech.solalat.user.databinding.RowMedicalCategoryBinding
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.adapters.BaseViewHolder
import com.raantech.solalat.user.utils.extensions.setPopUpAnimation
import com.raantech.solalat.user.utils.extensions.setSlideAnimation

class MedicalCategoriesRecyclerAdapter(
        context: Context
) : BaseBindingRecyclerViewAdapter<ServiceCategory>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
                RowMedicalCategoryBinding.inflate(
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

    inner class ViewHolder(private val binding: RowMedicalCategoryBinding) :
            BaseViewHolder<ServiceCategory>(binding.root) {

        override fun bind(item: ServiceCategory) {
            binding.item = item
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(it, adapterPosition, item)
            }
        }
    }
}