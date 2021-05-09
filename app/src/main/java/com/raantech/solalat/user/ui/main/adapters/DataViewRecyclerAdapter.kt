package com.raantech.solalat.user.ui.main.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raantech.solalat.user.data.models.DataView
import com.raantech.solalat.user.databinding.RowDataViewBinding
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.adapters.BaseViewHolder

class DataViewRecyclerAdapter(
        context: Context
) : BaseBindingRecyclerViewAdapter<DataView>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
                RowDataViewBinding.inflate(
                        LayoutInflater.from(context), parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class ViewHolder(private val binding: RowDataViewBinding) :
            BaseViewHolder<DataView>(binding.root) {

        override fun bind(item: DataView) {
            binding.item = item
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(it, adapterPosition, item)
            }
        }
    }
}