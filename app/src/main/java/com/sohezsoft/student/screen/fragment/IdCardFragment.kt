package com.sohezsoft.student.screen.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.sohezsoft.student.R
import com.sohezsoft.student.data.repository.api.NetworkResponse
import com.sohezsoft.student.data.repository.structure.personal.Personal
import com.sohezsoft.student.data.viewmodel.PersonalViewModel
import com.sohezsoft.student.databinding.FragmentIdCardBinding
import com.sohezsoft.student.screen.MainActivity

class IdCardFragment : Fragment() {

    private var _binding: FragmentIdCardBinding? = null
    private val binding get() = _binding!!
    private var mainActivity : MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Ensure that the hosting context is an instance of MainActivity
        if (context is MainActivity) {
            mainActivity = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View{
        // Inflate the layout for this fragment
        _binding = FragmentIdCardBinding.inflate(layoutInflater,container,false)


        mainActivity?.isLoading(true)


        mainActivity?.viewModel?.data?.observe(viewLifecycleOwner){
            when(it){
                is NetworkResponse.Loading ->{
                    mainActivity?.isLoading(true)
                   // Toast.makeText(requireActivity().applicationContext,"loading", Toast.LENGTH_SHORT).show()
                }

                is NetworkResponse.Success ->{
                    mainActivity?.isLoading(false)
                    setupUI(it.data)
                }

                is NetworkResponse.Error -> {
                    mainActivity?.isLoading(false)
                    Toast.makeText(requireActivity().applicationContext,it.exception.toString(),
                        Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }


    @SuppressLint("SetTextI18n")
    private fun setupUI(it: Personal){

        Glide.with(this)
            .load(it.photo_url)
            .placeholder(R.drawable.img_user_placeholder)
            .into(binding.imageViewPic)

        binding.textViewName.text = it.name
        binding.textViewAnswer.text = it.dob
        binding.textViewAdmissionDate.text = it.dob
        binding.textViewClass.text = "${it.course} ${it.course_subject} ${it.current_year}"
        binding.textViewIDNumber.text = it.student_id.toString()
        binding.textViewAddress.text = it.address

    }

    override fun onDestroyView() {
        //save memory Leak
        _binding = null
        mainActivity = null
        super.onDestroyView()
    }
}