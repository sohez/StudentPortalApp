package com.sohezsoft.student.adapter.exam

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sohezsoft.student.screen.fragment.exam.PreviousExamFragment
import com.sohezsoft.student.screen.fragment.exam.UpcomingExamFragment

class ExamTabLayoutAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UpcomingExamFragment()
            else -> PreviousExamFragment()
        }
    }
}
