package com.sohezsoft.student.screen.fees

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sohezsoft.student.R
import com.sohezsoft.student.adapter.FeesAdapter
import com.sohezsoft.student.data.repository.api.NetworkResponse
import com.sohezsoft.student.data.repository.api.RetrofitObject
import com.sohezsoft.student.data.repository.repo.FeesRepository
import com.sohezsoft.student.data.viewmodel.FeesViewModel
import com.sohezsoft.student.databinding.ActivityFeesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class FeesActivity : AppCompatActivity() {

    private var _binding : ActivityFeesBinding? = null
    private val binding get() = _binding!!

    private lateinit var loadingDialog : Dialog
    private lateinit var errorDialog: Dialog

    private lateinit var viewModel : FeesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFeesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding.includedLayout.textViewTitle.text = "Fees"
        //init the loading Dialog box, and set the Background transparent
        //set Custom layout.
        loadingDialog = Dialog(this)
        val window1 = loadingDialog.window
        window1?.setBackgroundDrawableResource(android.R.color.transparent)

        errorDialog = Dialog(this)
        errorDialog.setContentView(R.layout.layout_dialog)


        val apiService = RetrofitObject.apiService
        val repo = FeesRepository(apiService)
        viewModel = ViewModelProvider(this,FeesViewModel.FeesViewModelFactory(repo))[FeesViewModel::class.java]

        binding.includedLayout.recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = FeesAdapter()

        binding.includedLayout.recyclerView.adapter = adapter

        isLoading(true)

        viewModel.data.observe(this){
            if(it != null) {
                when (it) {
                    is NetworkResponse.Loading -> {
                        isLoading(true)
                    }

                    is NetworkResponse.Success -> {
                        isLoading(false)
                        adapter.submitList(it.data)
                    }

                    is NetworkResponse.Error -> {
                        isLoading(false)
                        showErrorDialog(it.exception.toString())
                    }
                }
            }else{
                //res null
            }
        }
    }

    private fun refreshData(){
        CoroutineScope(Dispatchers.IO).launch{
            viewModel.refreshData()
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

    //this method also access within Fragment using context
    //this take the one param for store the error msg.
    @SuppressLint("SetTextI18n")
    private fun showErrorDialog(msg: String) {
        errorDialog.setCancelable(false)

        val title = errorDialog.findViewById<TextView>(R.id.text_title)
        val _msg = errorDialog.findViewById<TextView>(R.id.text_msg)
        val buttonLeft = errorDialog.findViewById<Button>(R.id.button_left)
        val buttonRight = errorDialog.findViewById<Button>(R.id.button_right)

        title.text = "Error"
        _msg.text = msg
        buttonLeft.text = "Exit"
        buttonRight.text = "Try Again"

        buttonLeft.setOnClickListener {
            //Exit button clicked then finish activity
            finish()
        }
        buttonRight.setOnClickListener {
            //try again button click then get the Personal data.
            refreshData()
            errorDialog.hide()
        }
        errorDialog.show()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}