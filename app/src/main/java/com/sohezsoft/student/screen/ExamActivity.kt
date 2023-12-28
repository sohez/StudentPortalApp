package com.sohezsoft.student.screen

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sohezsoft.student.R
import com.sohezsoft.student.adapter.exam.ExamTabLayoutAdapter
import com.sohezsoft.student.data.repository.api.RetrofitObject
import com.sohezsoft.student.data.repository.repo.ExamRepository
import com.sohezsoft.student.data.viewmodel.ExamViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExamActivity : AppCompatActivity() {

    lateinit var viewModel: ExamViewModel

    //variable for creating the dialog box.
    private lateinit var loadingDialog : Dialog
    private lateinit var errorDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam)

        loadingDialog = Dialog(this)
        errorDialog = Dialog(this)
        errorDialog.setContentView(R.layout.layout_dialog)

        val swipLayout = findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        //On swipe And refresh then call the refreshData() method and refresh the data.
        swipLayout.setOnRefreshListener {
           refreshData()
            //after refreshing hide the loading icon
            swipLayout.isRefreshing = false
        }

        val window1 = loadingDialog.window
        window1?.setBackgroundDrawableResource(android.R.color.transparent)

        val apiservice = RetrofitObject.apiService
        val repo = ExamRepository(apiservice)
        viewModel = ViewModelProvider(this,ExamViewModel.ExamViewModelFactory(repo))[ExamViewModel::class.java]

        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)

        val adapter = ExamTabLayoutAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Upcoming Exam"
                1 -> tab.text = "Previous Exam"
            }
        }.attach()
    }

    private fun refreshData() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.refreshData()
        }
    }
    fun showErrorDialog(msg: String) {
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

    fun isLoading(loading: Boolean) {
        if(loadingDialog != null) {
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

}