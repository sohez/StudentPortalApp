package com.sohezsoft.student.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sohezsoft.student.data.repository.structure.calander.CalanderDay
import com.sohezsoft.student.data.repository.structure.personal.Notice
import com.sohezsoft.student.databinding.ItemNoticeBinding

class CalenderAdapter : ListAdapter<CalanderDay, CalenderAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNoticeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemNoticeBinding) : RecyclerView.ViewHolder(binding.root) {
        // Bind data to the views in your list item layout here
        fun bind(item: CalanderDay) {
            binding.itemTextTitle.text = item.date
            binding.itemTextDec.text = item.event
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<CalanderDay>() {
        override fun areItemsTheSame(oldItem: CalanderDay, newItem: CalanderDay): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: CalanderDay, newItem:CalanderDay): Boolean {
            return oldItem == newItem
        }
    }
}
