package com.sohezsoft.student.screen

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sohezsoft.student.R
import com.sohezsoft.student.adapter.CalenderAdapter
import com.sohezsoft.student.adapter.NoticeRecycleViewAdapter
import com.sohezsoft.student.data.repository.api.NetworkResponse
import com.sohezsoft.student.data.repository.api.RetrofitObject
import com.sohezsoft.student.data.repository.repo.CalenderRepository
import com.sohezsoft.student.data.viewmodel.CalenderViewModel
import com.sohezsoft.student.databinding.ActivityAcademicCalanderBinding

class AcademicCalanderActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAcademicCalanderBinding

    private lateinit var loadingDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAcademicCalanderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init the loading Dialog box, and set the Background transparent
        //set Custom layout.
        loadingDialog = Dialog(this)
        val window1 = loadingDialog.window
        window1?.setBackgroundDrawableResource(android.R.color.transparent)

        val apiService = RetrofitObject.apiService
        val repository = CalenderRepository(apiService)
        val viewModel = ViewModelProvider(this,CalenderViewModel.Factory(repository))[CalenderViewModel::class.java]

        val adapter = CalenderAdapter()
        binding.includeLayout.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.includeLayout.recyclerView.adapter = adapter
        binding.includeLayout.textViewTitle.text = "Acadmicm Calender"

        viewModel.data.observe(this){
            when(it){
                is NetworkResponse.Loading ->{
                    isLoading(true)
                }
                is NetworkResponse.Success -> {
                    isLoading(false)
                    adapter.submitList(it.data)
                }
                is NetworkResponse.Error ->{
                    isLoading(false)
                    Toast.makeText(applicationContext,it.exception.toString(),Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isLoading(loading: Boolean) {
        if (loading) {
            loadingDialog.setContentView(R.layout.layout_loading_box)
            loadingDialog.setCancelable(false)
            loadingDialog.show()
        } else {
            if (loadingDialog.isShowing) {
                loadingDialog.hide()
            }
        }
    }

}