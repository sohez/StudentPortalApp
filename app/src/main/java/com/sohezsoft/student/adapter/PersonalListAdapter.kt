package com.sohezsoft.student.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sohezsoft.student.data.repository.structure.card.CardRow
import com.sohezsoft.student.databinding.ItemPersonalRowDataBinding

class PersonalListAdapter : ListAdapter<CardRow, PersonalListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPersonalRowDataBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding : ItemPersonalRowDataBinding) : RecyclerView.ViewHolder(binding.root) {
        // Bind data to the views in your list item layout here
        fun bind(item: CardRow) {
            binding.textViewQuestion.text = item.title
            binding.textViewAnswer.text = item.value
            binding.imageViewIcon.setImageResource(item.icon)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<CardRow>() {
        override fun areItemsTheSame(oldItem: CardRow, newItem: CardRow): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: CardRow, newItem:CardRow): Boolean {
            return oldItem == newItem
        }
    }
}

