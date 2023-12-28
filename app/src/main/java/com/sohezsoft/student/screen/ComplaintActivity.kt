package com.sohezsoft.student.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sohezsoft.student.R
import com.sohezsoft.student.databinding.ActivityComplaintBinding

class ComplaintActivity : AppCompatActivity() {

    private lateinit var binding : ActivityComplaintBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComplaintBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.layoutBtn.buttonText.text = "Submit"

        binding.layoutID.icon = getDrawable(R.drawable.ic_idcad)
        binding.layoutID.hintText = "ID CARD NO"

        binding.layoutName.icon = getDrawable(R.drawable.ic_person)
        binding.layoutName.hintText = "Name"

        binding.layoutBtn.button.setOnClickListener {
            Toast.makeText(applicationContext,"submited",Toast.LENGTH_SHORT).show()
        }

    }
}