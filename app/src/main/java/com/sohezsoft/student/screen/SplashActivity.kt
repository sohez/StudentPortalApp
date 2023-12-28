package com.sohezsoft.student.screen

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.sohezsoft.student.R
import com.sohezsoft.student.data.repository.api.NetworkResponse
import com.sohezsoft.student.data.repository.api.RetrofitObject
import com.sohezsoft.student.data.repository.db.StudentDataBase
import com.sohezsoft.student.data.repository.db.preference.TokenPreference
import com.sohezsoft.student.databinding.ActivitySplashBinding
import com.sohezsoft.student.data.repository.repo.BasicDataRepository
import com.sohezsoft.student.data.viewmodel.BasicDataViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var viewModel: BasicDataViewModel
    private var handler : Handler? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //allow to cover all screen
        WindowCompat.setDecorFitsSystemWindows(window, false)

        //init the viewModel (BasicDataViewModel)
        val apiService = RetrofitObject.apiService
        val dao = StudentDataBase.instanceStudentDataBase(applicationContext).getDaoService()
        val repo = BasicDataRepository(apiService,applicationContext,dao)
        viewModel = ViewModelProvider(this,BasicDataViewModel.BasicDataViewModelFactory(repo))[BasicDataViewModel::class.java]

        //Get Data from Repo, if net available then hit API or Get from Local DB
        sendRequest()

        //set the LiveData Observer
        viewModel.splashdata.observe(this){
            when(it){
                is NetworkResponse.Loading ->{
                    //Loading State
                }
                is NetworkResponse.Success ->{
                   // Toast.makeText(applicationContext,it.data.toString(),Toast.LENGTH_SHORT).show()
                    changeScreen()
                }
                is NetworkResponse.Error -> {
                    showDialogBox(it.exception.toString())
                }
            }
        }
    }

    //Request to ViewModel for Data
    private fun sendRequest(){
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getSplashData()
        }
    }

    /*
    (Check Registered or Not)
    check if Register Token is Available or Not
    If Available then go to Main Activity,
    else go to Register Activity
     */
    private fun changeScreen(){
        val token = TokenPreference(this)
        val i = Intent()
        if (token.isToken()){
            i.setClass(this, MainActivity::class.java)
        }else{
            i.setClass(this, RegisterActivity::class.java)
        }
        startActivity(i)
        finish()
    }

    //create and Show the Dialog Box
    private fun showDialogBox(msg:String){

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.layout_dialog)
        dialog.setCancelable(false)
        val title = dialog.findViewById<TextView>(R.id.text_title)
        val _msg = dialog.findViewById<TextView>(R.id.text_msg)
        val buttonLeft = dialog.findViewById<Button>(R.id.button_left)
        val buttonRight = dialog.findViewById<Button>(R.id.button_right)

        title.text= "Error"
        _msg.text = msg
        buttonLeft.text = "Exit"
        buttonRight.text = "Try Again"

        buttonLeft.setOnClickListener {
            finish()
        }
        buttonRight.setOnClickListener {
            dialog.hide()
            sendRequest()
        }
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        //Release the handler, activity not open many time if fresh install..
            handler?.removeCallbacksAndMessages(null)
    }
}