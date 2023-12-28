package com.sohezsoft.student.adapter.exam

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sohezsoft.student.R
import com.sohezsoft.student.databinding.ItemExamPreviousBinding
import com.sohezsoft.student.data.repository.structure.exam.Previous
import com.sohezsoft.student.screen.fragment.exam.ExamDataViewActivity
import java.io.Serializable

class PreviousExamAdapter(private val context: Context): ListAdapter<Previous, PreviousExamAdapter.ViewHolder>(difutil()) {

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemExamPreviousBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemExamPreviousBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Previous){

            binding.incExamPreCourseName.textViewQuestion.text = "Course Name"
            binding.incExamPreCourseName.textViewAnswer.text = item.courseName
            binding.incExamPreCourseName.imageViewIcon.setImageResource(R.drawable.ic_course)

            binding.incExamPreSem.textViewQuestion.text = "Sem:"
            binding.incExamPreSem.textViewAnswer.text = item.sem.toString()
            binding.incExamPreSem.imageViewIcon.setImageResource(R.drawable.ic_calender)

            binding.incExamPreDateOfExam.textViewQuestion.text = "Date of Exam:"
            binding.incExamPreDateOfExam.textViewAnswer.text = item.dateOfExam
            binding.incExamPreDateOfExam.imageViewIcon.setImageResource(R.drawable.ic_calender)

            binding.incExamPrePass.textViewQuestion.text = "Pass:"
            binding.incExamPrePass.textViewAnswer.text = item.isPass.toString()
            binding.incExamPrePass.imageViewIcon.setImageResource(R.drawable.ic_person)

            binding.btnResult.setOnClickListener {
                val i = Intent(context, ExamDataViewActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                i.putExtra("item",3)
                i.putExtra("data", item.result as Serializable)
                context.startActivity(i)
            }
        }
    }

    private class difutil : DiffUtil.ItemCallback<Previous>() {
        override fun areItemsTheSame(oldItem: Previous, newItem: Previous): Boolean {
            return oldItem.sem == newItem.sem
        }

        override fun areContentsTheSame(oldItem: Previous, newItem: Previous): Boolean {
            return oldItem == newItem
        }
    }

}