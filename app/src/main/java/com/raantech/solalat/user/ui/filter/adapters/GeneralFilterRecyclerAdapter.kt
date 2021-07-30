package com.raantech.solalat.user.ui.filter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.models.GeneralLookup
import com.raantech.solalat.user.databinding.RowFilterBinding
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.adapters.BaseViewHolder
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class GeneralFilterRecyclerAdapter @Inject constructor(
    @ActivityContext context: Context
) : BaseBindingRecyclerViewAdapter<GeneralLookup>(context) {

    var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImageViewHolder(
            RowFilterBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ImageViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class ImageViewHolder(private val binding: RowFilterBinding) :
        BaseViewHolder<GeneralLookup>(binding.root) {

        override fun bind(item: GeneralLookup) {
            binding.item = item
            binding.lifecycleOwner = context as AppCompatActivity
            binding.root.setOnClickListener {
                if (selectedPosition != -1) {
                    items[selectedPosition].selected.value = false
                }
                selectedPosition = adapterPosition
                item.selected.value = true
                itemClickListener?.onItemClick(it, adapterPosition, item)
            }
        }
    }

}