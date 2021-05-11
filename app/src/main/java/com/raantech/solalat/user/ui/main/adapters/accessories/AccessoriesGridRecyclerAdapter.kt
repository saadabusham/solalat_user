package com.raantech.solalat.user.ui.main.adapters.accessories

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raantech.solalat.user.data.models.accessories.Accessory
import com.raantech.solalat.user.data.models.truck.Truck
import com.raantech.solalat.user.databinding.RowAccessoryBinding
import com.raantech.solalat.user.databinding.RowTruckBinding
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.adapters.BaseViewHolder

class AccessoriesGridRecyclerAdapter(
    context: Context
) : BaseBindingRecyclerViewAdapter<Accessory>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            RowAccessoryBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class ViewHolder(private val binding: RowAccessoryBinding) :
        BaseViewHolder<Accessory>(binding.root) {

        override fun bind(item: Accessory) {
            binding.item = item
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(it, adapterPosition, item)
            }
        }
    }
}