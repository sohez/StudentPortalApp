package com.sohezsoft.student.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sohezsoft.student.R
import com.sohezsoft.student.data.repository.structure.fees.FeesItem
import com.sohezsoft.student.databinding.ItemFeesBinding
import com.sohezsoft.student.databinding.ItemPersonalRowDataBinding

class FeesAdapter : ListAdapter<FeesItem,FeesAdapter.ViewHolder>(dif()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeesAdapter.ViewHolder {
       val binding = ItemFeesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeesAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding:ItemFeesBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(item : FeesItem){
            setItem(
                binding.LayoutFeesType,
                "Fees Type",
                item.fees_type,
                R.drawable.ic_fees
            )
            setItem(
                binding.LayoutProgram,
                "Program",
                item.program,
                R.drawable.ic_fees
            )
            setItem(
                binding.LayoutStatues,
                "Statues",
                item.statues,
                R.drawable.ic_fees
            )
            setItem(
                binding.LayoutTotalAmount,
                "Total Amount",
                item.total_amount.toString(),
                R.drawable.ic_fees
            )
            setItem(
                binding.LayoutPendingAmount,
                "Pending Amount",
                item.pending_amount.toString(),
                R.drawable.ic_fees
            )
            setItem(
                binding.LayoutPaidAmount,
                "Paid Amount",
                item.paid_amount.toString(),
                R.drawable.ic_fees
            )
            setItem(
                binding.LayoutDueDate,
                "Due Date",
                item.due_date,
                R.drawable.ic_fees
            )
            setItem(
                binding.LayoutLastUpdate,
                "Last Update",
                item.last_update_date,
                R.drawable.ic_fees
            )
        }

        private fun setItem(layout: ItemPersonalRowDataBinding, title:String, value:String, icon:Int){
            layout.textViewQuestion.text = title
            layout.textViewAnswer.text = value
            layout.imageViewIcon.setImageResource(icon)
        }
    }

    private class dif : DiffUtil.ItemCallback<FeesItem>(){
        override fun areItemsTheSame(oldItem: FeesItem, newItem: FeesItem): Boolean {
            return oldItem.fees_type == newItem.fees_type
        }

        override fun areContentsTheSame(oldItem: FeesItem, newItem: FeesItem): Boolean {
            return oldItem == newItem
        }

    }

}