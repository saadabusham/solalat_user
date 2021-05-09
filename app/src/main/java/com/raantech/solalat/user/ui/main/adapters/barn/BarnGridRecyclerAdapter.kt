package com.raantech.solalat.user.ui.main.adapters.barn

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raantech.solalat.user.data.models.barn.Barn
import com.raantech.solalat.user.databinding.RowBarnBinding
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.adapters.BaseViewHolder

class BarnGridRecyclerAdapter(
        context: Context
) : BaseBindingRecyclerViewAdapter<Barn>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
                RowBarnBinding.inflate(
                        LayoutInflater.from(context), parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class ViewHolder(private val binding: RowBarnBinding) :
            BaseViewHolder<Barn>(binding.root) {

        override fun bind(item: Barn) {
            binding.item = item
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(it, adapterPosition, item)
            }
        }
    }
}