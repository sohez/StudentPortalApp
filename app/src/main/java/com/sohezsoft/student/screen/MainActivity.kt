package com.sohezsoft.student.screen

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sohezsoft.student.R
import com.sohezsoft.student.data.repository.api.RetrofitObject
import com.sohezsoft.student.data.repository.db.StudentDataBase
import com.sohezsoft.student.data.repository.repo.PersonalRepository
import com.sohezsoft.student.data.viewmodel.PersonalViewModel
import com.sohezsoft.student.databinding.ActivityMainBinding
import com.sohezsoft.student.screen.fragment.HomeFragment
import com.sohezsoft.student.screen.fragment.IdCardFragment
import com.sohezsoft.student.screen.fragment.NoticeFragment
import com.sohezsoft.student.screen.fragment.PersonalDetailsFragment
import com.sohezsoft.student.screen.fragment.SettingFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    //layout binding Variable, nullable becoz onDestroy we assign the null for save memory leak..
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    //do not create a viewmodel variable private,
    // this variable access in the fragment using context of main activity OnAttach Fragment
    var viewModel: PersonalViewModel? = null

    //variable for creating the dialog box.
    private lateinit var loadingDialog: Dialog
    private lateinit var errorDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //This code will create a full-screen window,
        //with the window decoration drawn over the system windows. like status bar and navigation bar.
        WindowCompat.setDecorFitsSystemWindows(window, false)

        //init the loading Dialog box, and set the Background transparent
        //set Custom layout.
        loadingDialog = Dialog(this)
        val window1 = loadingDialog.window
        window1?.setBackgroundDrawableResource(android.R.color.transparent)

        //init the error Dialog box and set Custom layout.
        errorDialog = Dialog(this)
        errorDialog.setContentView(R.layout.layout_dialog)

        //creating & init the object of API, ROOM DB, and Repository
        val apiService = RetrofitObject.apiService
        val dao = StudentDataBase.instanceStudentDataBase(this).getDaoService()
        val repo = PersonalRepository(apiService)

        //init the viewModel this providing Owner this
        //this ViewModel is Access by Fragments
        //we Using the ViewModel provider for creating instance & replace the '.get()' into [] operator.
        viewModel = ViewModelProvider(this, PersonalViewModel.PersonalViewModelFactory(repo))[PersonalViewModel::class.java]

        //load the Fragment that is saved into ViewModel. else load default HomeFragment()
        replaceFragment(viewModel!!.page)

        //when bottom nav item click then set the item id into viewmodel, for later use
        // and load the selected fragment
        binding.bottomMenu.setOnItemSelectedListener { item ->
            if(item.itemId != binding.bottomMenu.selectedItemId) {
                viewModel!!.setPageID(item.itemId)
                replaceFragment(item.itemId)
            }
            true
        }


        //test code kinly remove it
//        val edt = findViewById<TextView>(R.id.text_msg)
//         edt.text = ""
//        edt.setOnClickListener {
//
//        }

    }

    //this Method is use to Fetch the PersonalData Api
    //this method also access within Fragment using context
    fun refreshData() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel!!.refreshPersonalData()
        }
    }

    //this method also access within Fragment using context
    //if pass true then show Box else Hide
    fun isLoading(loading: Boolean) {
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

    //this method load the fragment using provided bottom nav itemId
    private fun replaceFragment(itemId: Int) {
        when (itemId) {
            R.id.navigation_home -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_loader, HomeFragment())
                    .commit()
            }

            R.id.navigation_notice -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_loader, NoticeFragment())
                    .commit()
            }

            R.id.navigation_idCard -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_loader, IdCardFragment())
                    .commit()
            }

            R.id.navigation_setting -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_loader, SettingFragment())
                    .commit()
            }

            R.id.navigation_PersonalDetails -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_loader, PersonalDetailsFragment())
                    .commit()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //assign the null vale for save the memory leaks
        _binding = null
        viewModel = null
    }
}