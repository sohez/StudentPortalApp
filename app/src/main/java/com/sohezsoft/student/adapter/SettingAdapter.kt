package com.sohezsoft.student.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sohezsoft.student.data.repository.structure.setting.SettingItemData
import com.sohezsoft.student.databinding.ItemSettingsBinding

class SettingAdapter(private val callBack: SettingItemClickEvent): ListAdapter<SettingItemData,SettingAdapter.ViewHolder>(dif()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingAdapter.ViewHolder {
        val binding = ItemSettingsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SettingAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            callBack.settingItemClicked(item.id)
        }
    }

    class ViewHolder(private val binding:ItemSettingsBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(settingItemData: SettingItemData){
            binding.setting.imageViewIcon.setImageResource(settingItemData.icon)
            binding.setting.textViewQuestion.text= settingItemData.name
            binding.setting.textViewQuestion.setTextColor(Color.BLACK)
            binding.setting.textViewAnswer.visibility = View.GONE
        }
    }

    private class dif : DiffUtil.ItemCallback<SettingItemData>(){
        override fun areItemsTheSame(oldItem: SettingItemData, newItem: SettingItemData): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SettingItemData,
            newItem: SettingItemData
        ): Boolean {
            return oldItem == newItem
        }

    }

    interface SettingItemClickEvent {
        fun settingItemClicked(itemId: Int)
    }
}