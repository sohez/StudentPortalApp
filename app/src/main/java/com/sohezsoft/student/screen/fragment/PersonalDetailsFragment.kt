package com.sohezsoft.student.screen.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.sohezsoft.student.R
import com.sohezsoft.student.adapter.PersonalListAdapter
import com.sohezsoft.student.data.repository.api.NetworkResponse
import com.sohezsoft.student.data.repository.structure.card.CardRow
import com.sohezsoft.student.data.repository.structure.personal.Personal
import com.sohezsoft.student.data.viewmodel.PersonalViewModel
import com.sohezsoft.student.databinding.FragmentPersonalDetailsBinding
import com.sohezsoft.student.screen.MainActivity

class PersonalDetailsFragment : Fragment() {


    private var _binding: FragmentPersonalDetailsBinding? = null
    private val binding get() = _binding!!

    private var mainActivity: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mainActivity = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPersonalDetailsBinding.inflate(layoutInflater, container, false)
        //return Root View
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //show the loading Dialog Box
        mainActivity?.isLoading(true)

        mainActivity?.viewModel?.data?.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResponse.Loading -> {
                    mainActivity?.isLoading(true)
                    // Toast.makeText(requireActivity().applicationContext,"loading", Toast.LENGTH_SHORT).show()
                }

                is NetworkResponse.Success -> {
                    mainActivity?.isLoading(false)
                    setupUI(it.data)
                }

                is NetworkResponse.Error -> {
                    mainActivity?.isLoading(false)
//                    Toast.makeText(requireActivity().applicationContext,it.exception.toString(),
//                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupUI(personal: Personal) {

        val data: List<CardRow> = listOf(
            CardRow(1, "Name:", personal.name, R.drawable.ic_person),
            CardRow(2, "Student ID:", personal.student_id.toString(), R.drawable.ic_idcad),
            CardRow(3, "Course:", personal.course + " " + personal.course_subject, R.drawable.ic_course),
            CardRow(4, "Current Sem:", personal.current_year.toString(), R.drawable.ic_calender),
            CardRow(5, "Phone No:", personal.phone_no.joinToString(separator = ", "), R.drawable.ic_phone),
            CardRow(6, "Email:", personal.email_id , R.drawable.ic_email),
            CardRow(7, "Address:", personal.address , R.drawable.ic_location),
            CardRow(8, "Date of Birth:", personal.dob , R.drawable.ic_calender),
            CardRow(9, "Blood Group:", personal.blood_group , R.drawable.ic_syrenge),
            CardRow(10, "Father Name:",  personal.parents.father_name , R.drawable.ic_person),
            CardRow(11, "Father Phone No:",  personal.parents.father_phone.toString() , R.drawable.ic_phone),
            CardRow(12, "Mother Name:",  personal.parents.mother_name , R.drawable.ic_person),
            CardRow(13,  "Mother Phone No:",  personal.parents.mother_phone.toString(), R.drawable.ic_phone),
            CardRow(14, "Parents Address:",  personal.parents.address , R.drawable.ic_location),
        )

        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleView.hasFixedSize()

        val adapter = PersonalListAdapter()
        adapter.submitList(data)

        binding.recycleView.adapter  = adapter

        Glide.with(this)
            .load(personal.photo_url)
            .placeholder(R.drawable.img_user_placeholder)
            .into(binding.imageView4)

    }

    override fun onDestroyView() {
        // Release the binding object to avoid memory leaks
        _binding = null
        mainActivity = null
        super.onDestroyView()
    }
}