package com.sohezsoft.student.screen


import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import com.sohezsoft.student.data.repository.api.NetworkResponse
import com.sohezsoft.student.data.repository.api.RetrofitObject
import com.sohezsoft.student.data.repository.repo.RegisterRepository
import com.sohezsoft.student.data.viewmodel.RegisterViewModel
import com.sohezsoft.student.databinding.ActivityRegisterBinding
import com.sohezsoft.student.helper.HelperClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import android.content.ContentValues
import android.content.ContentResolver
import android.net.Uri
import android.provider.CalendarContract
import android.provider.CalendarContract.Events
import android.content.Intent
import com.sohezsoft.student.R
import com.sohezsoft.student.data.repository.db.preference.TokenPreference

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var loadingDialog : Dialog

    private val helperClass by lazy {
        HelperClass()
    }
    private var isEmail = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init the loading Dialog box, and set the Background transparent
        //set Custom layout.
        loadingDialog = Dialog(this)
        val window1 = loadingDialog.window
        window1?.setBackgroundDrawableResource(android.R.color.transparent)

        //create and init the instance of Retrofit, Repo, ViewModel
        val obj = RetrofitObject.apiService
        val registerRepository = RegisterRepository(obj, applicationContext)
        viewModel = ViewModelProvider(this, RegisterViewModel.RegisterViewModelFactory(registerRepository))[RegisterViewModel::class.java]

        //Observing the response of Signup API
        viewModel.resSignup.observe(this) {
            when (it) {
                is NetworkResponse.Loading -> {
                    isLoading(true)
                }

                is NetworkResponse.Error -> {
                    isLoading(false)
                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                }

                is NetworkResponse.Success -> {
                    isLoading(false)
                    TokenPreference(applicationContext).setToken(it.data.student_key)
                }
            }
        }

        //set First Layout
        setLayoutLogin()

        /*
        - On back Press (Old method is Deprecated)
        - condition check the Login Layout is Visible if visible then finish activity,
          else set the Login Layout
         */
        onBackPressedDispatcher.addCallback(this) {
            if (binding.isVisibleLogin == View.VISIBLE) {
                finish()
            } else {
                setLayoutLogin()
            }
        }
    }


    private fun setLayoutSignUp() {

        //using binding Visibility of included layout define
        binding.isVisibleSignUp = View.VISIBLE
        binding.isVisibleLogin = View.GONE
        binding.isVisibleForgotPass = View.GONE

        //set the Title and Description
        binding.textViewTitle.text = "Create a new Account"
        binding.textViewDesc.text = "Please Fill in the forms to Continue"
        //create the included Signup Layout variable
        val signupLayout = binding.layoutSignup

        /*
        - access the element from the included layout
        - Note - this variable is EditText Not the value Of EditText if you want to value then add the .text
          like - val name = signupLayout.name.EditText.text
        */

        val name = signupLayout.name.EditText
        val pass = signupLayout.pass.EditText
        val rPass = signupLayout.repeatPass.EditText
        val phoneNo = signupLayout.phoneNo.EditText
        val email = signupLayout.email.EditText

        //apply the filter for input (EditText)
        phoneNo.inputType = InputType.TYPE_CLASS_NUMBER
        phoneNo.filters = arrayOf( InputFilter.LengthFilter(10))

        //set the TextWatcher Object for validating the input text
        edittextWatcher(pass, signupLayout.textInvalid)

        //in the signup layout, SignUp Button click Event
        signupLayout.signupButton.button.setOnClickListener {
            val st = """
                { 
                "name":"sohez"
                }
                """.trimIndent()
            val data = JSONObject(st)
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.signup(data)
            }
        }

        //in the signup layout, Bottom login TextView click Event
        signupLayout.textLinkLogin.setOnClickListener {
            setLayoutLogin()
        }
    }

    private fun setLayoutLogin() {
        //using binding Visibility of included layout define
        binding.isVisibleSignUp = View.GONE
        binding.isVisibleLogin = View.VISIBLE
        binding.isVisibleForgotPass = View.GONE

        //set the title and description
        binding.textViewTitle.text = "Welcome Back"
        binding.textViewDesc.text = "We're happy to see you again to use your account. you shoud log in first."

        //create the included Login Layout variable
        val loginLayout = binding.layoutLogin

        /*
        - access the element from the included layout
        - Note - this variable is EditText Not the value Of EditText if you want to value then add the .text
                 like -  val passEditText = loginLayout.pass.EditText.text
        */

        val emailEditText = loginLayout.email.EditText
        val passEditText = loginLayout.pass.EditText

        //set the TextWatcher Object for validating the input text
        edittextWatcher(emailEditText, loginLayout.textInvalid)

        //Login Button click Event
        loginLayout.loginButton.button.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
            val st = """
                { 
                "name":"sohez"
                }
                """.trimIndent()

            CoroutineScope(Dispatchers.IO).launch {
                viewModel.signup(JSONObject(st))
            }
//            if (isEmail) {
//                Toast.makeText(applicationContext, "login", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(applicationContext, "Check pass", Toast.LENGTH_SHORT).show()
//            }
        }

        //Forgot Pass TextView Click Event
        loginLayout.forgotPass.setOnClickListener {
            setLayoutForgotPass()
        }

        //Bottom SignUp TextView Click Event
        loginLayout.textLinkSignUp.setOnClickListener {
            setLayoutSignUp()
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


    private fun setLayoutForgotPass() {
        //using binding Visibility of included layout define
        binding.isVisibleForgotPass = View.VISIBLE
        binding.isVisibleSignUp = View.GONE
        binding.isVisibleLogin = View.GONE

        //set the title and description
        binding.textViewTitle.text = "Recover Account"
        binding.textViewDesc.text = "Please Fill in the forms to Continue"
        //create the included Forgot Password Layout variable
        val forgotPassLayout = binding.layoutForgotPass

        /*
         - access the element from the included layout
         - Note - this variable is EditText Not the value Of EditText if you want to value then add the .text
          like -   val email = forgotPassLayout.email.EditText.text
        */
        val email = forgotPassLayout.email.EditText
        val pass = forgotPassLayout.pass.EditText
        val otp = forgotPassLayout.otp.EditText

        //apply the filter for input (EditText)
        otp.inputType = InputType.TYPE_CLASS_NUMBER
        otp.filters = arrayOf( InputFilter.LengthFilter(6))
        email.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        pass.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD

        email.isEnabled = true
        forgotPassLayout.llOtpExpand.visibility = View.GONE
        forgotPassLayout.sendButton.buttonText.text= "Send OTP"

        //click on Otp send Button
        forgotPassLayout.sendButton.button.setOnClickListener {
            if(email.isEnabled) {
                forgotPassLayout.llOtpExpand.visibility = View.VISIBLE
                email.isEnabled = false
                //send OTP
                forgotPassLayout.sendButton.buttonText.text= "Update"
            }else{
                //Update Password

            }
        }
        forgotPassLayout.textLinkSignUp.setOnClickListener {
            setLayoutSignUp()
        }

    }

    private fun edittextWatcher(editText: EditText, textView: TextView) {

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val str = helperClass.isValidPassword(editText.text.toString())
                if (str.isNullOrEmpty()) {
                    textView.visibility = View.GONE
                    isEmail = true
                } else {
                    textView.visibility = View.VISIBLE
                    textView.text = str
                    isEmail = false
                }
                if (p0.isNullOrEmpty()) {
                    textView.visibility = View.GONE
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }
}