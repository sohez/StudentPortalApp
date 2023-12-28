package com.sohezsoft.student.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sohezsoft.student.data.repository.structure.personal.Notice
import com.sohezsoft.student.databinding.ItemNoticeBinding

class NoticeRecycleViewAdapter : ListAdapter<Notice, NoticeRecycleViewAdapter.ViewHolder>(DiffCallback()) {

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
        fun bind(item: Notice) {
            binding.itemTextTitle.text = item.title
            binding.itemTextDec.text = item.description
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Notice>() {
        override fun areItemsTheSame(oldItem: Notice, newItem: Notice): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Notice, newItem:Notice): Boolean {
            return oldItem == newItem
        }
    }
}
