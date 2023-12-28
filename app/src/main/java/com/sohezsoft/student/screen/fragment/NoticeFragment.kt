package com.sohezsoft.student.screen.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sohezsoft.student.adapter.NoticeRecycleViewAdapter
import com.sohezsoft.student.data.repository.api.NetworkResponse
import com.sohezsoft.student.data.repository.structure.personal.Notice
import com.sohezsoft.student.data.viewmodel.PersonalViewModel
import com.sohezsoft.student.databinding.FragmentNoticeBinding
import com.sohezsoft.student.screen.MainActivity


class NoticeFragment : Fragment() {

    private var _binding: FragmentNoticeBinding? = null
    private val binding get() = _binding!!

    private var mainActivity: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mainActivity = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNoticeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.includedLayout.textViewTitle.text = "Notice"
        mainActivity?.isLoading(true)

        mainActivity?.viewModel?.data?.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResponse.Loading -> {
                    mainActivity?.isLoading(true)
                }

                is NetworkResponse.Success -> {
                    mainActivity?.isLoading(false)
                    setupRecycle(it.data.personal_notice)
                }

                is NetworkResponse.Error -> {
                    mainActivity?.isLoading(false)
                    mainActivity?.showErrorDialog(it.exception.toString())
                }
            }
        }
    }

    private fun setupRecycle(data: List<Notice>) {

        val adapter = NoticeRecycleViewAdapter()

        adapter.submitList(data)
        val recyclerView = binding.includedLayout.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(mainActivity)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        _binding = null
        mainActivity = null
        super.onDestroy()
    }

}