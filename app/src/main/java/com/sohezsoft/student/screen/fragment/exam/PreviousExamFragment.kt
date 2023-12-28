package com.sohezsoft.student.screen.fragment.exam

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sohezsoft.student.adapter.exam.PreviousExamAdapter
import com.sohezsoft.student.data.repository.api.NetworkResponse
import com.sohezsoft.student.databinding.FragmentPreviousExamBinding
import com.sohezsoft.student.screen.ExamActivity



class PreviousExamFragment : Fragment() {

    private var _binding : FragmentPreviousExamBinding? = null
    private val binding get() = _binding!!

    private var examActivity : ExamActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is ExamActivity){
            examActivity = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =  FragmentPreviousExamBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        examActivity?.isLoading(true)

        val adapter = PreviousExamAdapter(requireContext())
        binding.includedLayout.textViewTitle.visibility = View.GONE
        binding.includedLayout.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.includedLayout.recyclerView.adapter = adapter

        examActivity?.viewModel?.previous?.observe(viewLifecycleOwner){
            if(it!=null){
                when (it) {
                    is NetworkResponse.Loading -> {
                        //  binding.text.text = "loading"
                        examActivity?.isLoading(true)
                    }

                    is NetworkResponse.Success -> {
                        examActivity?.isLoading(false)
                        // binding.text.text = it.data.toString()
                        adapter.submitList(it.data)
                        //setUpRecycleView(it.data)
                    }

                    is NetworkResponse.Error -> {
                        examActivity?.isLoading(false)
                        examActivity?.showErrorDialog(it.exception.toString())
                        //  binding.text.text = it.exception.toString()
                    }
                }
            }else{

            }
        }

    }

    private fun setUpRecycleView(){

    }
}