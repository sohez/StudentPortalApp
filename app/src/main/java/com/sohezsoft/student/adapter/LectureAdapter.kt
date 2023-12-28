package com.sohezsoft.student.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sohezsoft.student.R
import com.sohezsoft.student.data.repository.structure.lecture.Lecture
import com.sohezsoft.student.databinding.ItemLectureBinding
import com.sohezsoft.student.databinding.ItemPersonalRowDataBinding

class LectureAdapter : ListAdapter<Lecture, LectureAdapter.ViewHolder>(dif()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LectureAdapter.ViewHolder {
        val binding = ItemLectureBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LectureAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemLectureBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(item : Lecture){
            setItem(
                binding.day,
                "Day",
                item.day,
                R.drawable.ic_fees
            )
            setItem(
                binding.subject,
                "Subject",
                item.subject,
                R.drawable.ic_fees
            )
            setItem(
                binding.teacher,
                "Teacher",
                item.teacher,
                R.drawable.ic_fees
            )
            setItem(
                binding.time,
                "Time",
                item.time,
                R.drawable.ic_fees
            )
            setItem(
                binding.type,
                "Theory/Practicle",
                item.type,
                R.drawable.ic_fees
            )
            setItem(
                binding.teacherId,
                "Teacher Id",
                item.teacher_id.toString(),
                R.drawable.ic_fees
            )
        }

        private fun setItem(layout: ItemPersonalRowDataBinding, title:String, value:String, icon:Int){
            layout.textViewQuestion.text = title
            layout.textViewAnswer.text = value
            layout.imageViewIcon.setImageResource(icon)
        }
    }

    private class dif : DiffUtil.ItemCallback<Lecture>(){
        override fun areItemsTheSame(oldItem: Lecture, newItem: Lecture): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Lecture, newItem: Lecture): Boolean {
            return oldItem == newItem
        }

    }

}