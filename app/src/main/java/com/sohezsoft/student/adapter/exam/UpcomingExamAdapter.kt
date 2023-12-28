package com.sohezsoft.student.adapter.exam

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sohezsoft.student.R
import com.sohezsoft.student.data.repository.structure.exam.TimeTable
import com.sohezsoft.student.data.repository.structure.exam.Upcoming
import com.sohezsoft.student.databinding.ItemExamUpcomingBinding
import com.sohezsoft.student.databinding.ItemPersonalRowDataBinding
import com.sohezsoft.student.screen.fragment.exam.ExamDataViewActivity
import java.io.Serializable


class UpcomingExamAdapter(private val context: Context): ListAdapter<Upcoming, UpcomingExamAdapter.ViewHolder>(
    DiffUtilItemCallback()
) {

    //Inflate the layout item_exam_upcoming.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemExamUpcomingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        //pass the inflated binding into ViewHolder Object and Return it..
        return ViewHolder(binding)
    }

    //pass the item into bind() using instance of ViewHolder.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        //sending item into ViewHolder using, instance holder
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemExamUpcomingBinding): RecyclerView.ViewHolder(binding.root){

        //this method Bind The Our View and Data
        //we create the methods to set the included layouts..
        //this "item" come from onBindViewHolder()
        fun bind(item: Upcoming){

            setItem(
                binding.incLayoutCourse,
                "Course Name:",
                item.courseName,
                R.drawable.ic_course
            )
            setItem(
                binding.incLayoutSem,
                "Sem:",
                item.sem.toString(),
                R.drawable.ic_calender
            )

            //this is set the paper type like - Theory or practical, MCQ..
            setItem(
                binding.incLayoutDate,
                "Paper Type:",
                item.paperType,
                R.drawable.ic_calender
            )

            //check the if Exam is BackLog/ Regular.
            val backlog = if(item.isRegular){
                "Regular"
            }else{
                "BackLog"
            }
            setItem(
                binding.incLayoutBackLog,
                "Regular/BackLog:",
                backlog,
                R.drawable.ic_bell
            )

            //check the Student is eligible for Exam or Not, with Reason.
            val msg = if(!item.eligible) {
                "No,\n${item.notEligibleMessage}"
            }else{
                "Yes"
            }

            setItem(
                binding.incLayoutNotice,
                "Eligible for Exam:",
                msg,
                R.drawable.ic_bell
            )

            setItem(
                binding.incLayoutYear,
                "Exam Seat No:",
                item.examSeatNo,
                R.drawable.ic_calender
            )

            //Click Listener for Time Table click Event..
            binding.btnTimeTable.setOnClickListener {
                //here is show the time table
                val i = Intent(context, ExamDataViewActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                i.putExtra("item",1)
                i.putExtra("data", item.timeTable as Serializable)
                context.startActivity(i)
            }

            //hide btn
            binding.btnHallTicket.visibility = View.GONE

        }

        //this method is use to set Text, icon to Specific included layout.
        //included layout pass using `binding.IncludedLayout` and that type is ItemPersonalRowDataBinding.
        private fun setItem(layout: ItemPersonalRowDataBinding, title:String, value:String, icon:Int){
            layout.textViewQuestion.text = title
            layout.textViewAnswer.text = value
            layout.imageViewIcon.setImageResource(icon)
        }
    }

    //the DiffUtil class to efficiently update a RecyclerView adapter.
    //comparing the elements..
    private class DiffUtilItemCallback : DiffUtil.ItemCallback<Upcoming>(){
        override fun areItemsTheSame(oldItem: Upcoming, newItem: Upcoming): Boolean {
            return oldItem.sem == newItem.sem
        }

        override fun areContentsTheSame(oldItem: Upcoming, newItem: Upcoming): Boolean {
            return oldItem == newItem
        }

    }
}