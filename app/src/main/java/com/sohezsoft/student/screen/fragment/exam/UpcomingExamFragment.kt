package com.sohezsoft.student.screen.fragment.exam

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sohezsoft.student.adapter.exam.UpcomingExamAdapter
import com.sohezsoft.student.data.repository.api.NetworkResponse
import com.sohezsoft.student.databinding.FragmentUpcomingExamBinding
import com.sohezsoft.student.screen.ExamActivity


class UpcomingExamFragment : Fragment() {

    private var _binding: FragmentUpcomingExamBinding? = null
    private val binding get() = _binding!!

    private var examActivity: ExamActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is ExamActivity){
            examActivity = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        _binding = FragmentUpcomingExamBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        examActivity?.isLoading(true)

        binding.includedLayout.textViewTitle.visibility = View.GONE
        binding.includedLayout.recyclerView.layoutManager = LinearLayoutManager(examActivity)
        val adapter = UpcomingExamAdapter(requireContext())
        binding.includedLayout.recyclerView.adapter = adapter

        examActivity!!.viewModel.upcoming.observe(viewLifecycleOwner){
            if(it != null) {
                when (it) {
                    is NetworkResponse.Loading -> {
                        examActivity?.isLoading(true)
                    }

                    is NetworkResponse.Success -> {
                        examActivity?.isLoading(false)
                        adapter.submitList(it.data)
                    }

                    is NetworkResponse.Error -> {
                        examActivity?.isLoading(false)
                        examActivity?.showErrorDialog(it.exception.toString())
                    }
                }
            }else{
                //kindly hide loading bar
                examActivity?.isLoading(false)
                Toast.makeText(examActivity,"empty Data",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        examActivity?.isLoading(false)
        examActivity = null
        super.onDestroy()
    }
}