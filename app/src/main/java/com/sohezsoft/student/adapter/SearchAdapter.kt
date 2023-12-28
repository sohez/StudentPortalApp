package com.sohezsoft.student.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sohezsoft.student.adapter.callback.CardItemClickListener
import com.sohezsoft.student.data.repository.structure.card.Cards
import com.sohezsoft.student.databinding.ItemPersonalRowDataBinding

class SearchAdapter(private val cardItemClickListener: CardItemClickListener) :
        ListAdapter<Cards, SearchAdapter.ViewHolder>(DiffUtilCallBack()) {

        inner class ViewHolder(private val binding: ItemPersonalRowDataBinding) : RecyclerView.ViewHolder(binding.root) {

            fun bind(item: Cards) {

                binding.textViewQuestion.visibility = View.GONE
                binding.textViewAnswer.text = item.title
                binding.imageViewIcon.setImageResource(item.icon)
                binding.constraintRoot.setOnClickListener{
                    cardItemClickListener.cardItemClicked(item.id)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemPersonalRowDataBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = getItem(position)
            holder.bind(item)
        }

        private class DiffUtilCallBack : DiffUtil.ItemCallback<Cards>() {
            override fun areItemsTheSame(oldItem: Cards, newItem: Cards): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Cards, newItem: Cards): Boolean {
                return oldItem == newItem
            }

        }
    }
