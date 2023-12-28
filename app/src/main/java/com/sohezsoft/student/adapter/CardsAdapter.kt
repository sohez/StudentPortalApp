package com.sohezsoft.student.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sohezsoft.student.adapter.callback.CardItemClickListener
import com.sohezsoft.student.data.repository.structure.card.Cards
import com.sohezsoft.student.databinding.ItemCardsBinding
import com.sohezsoft.student.screen.ExamActivity
import com.sohezsoft.student.screen.fees.FeesActivity
import com.sohezsoft.student.screen.lecture.LectureActivity

class CardsAdapter(private val cardItemClickListener: CardItemClickListener) :
    ListAdapter<Cards, CardsAdapter.ViewHolder>(DiffUtilCallBack()) {

    inner class ViewHolder(private val binding: ItemCardsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Cards) {

            binding.cardText.text = item.title
            binding.icCardIcon.setImageResource(item.icon)

            binding.cardView.setOnClickListener {
                cardItemClickListener.cardItemClicked(item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCardsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
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