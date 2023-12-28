package com.sohezsoft.student.screen.fragment.exam

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sohezsoft.student.data.repository.structure.exam.Result
import com.sohezsoft.student.data.repository.structure.exam.TimeTable
import com.sohezsoft.student.databinding.ActivityExamDataViewBinding
import com.sohezsoft.student.helper.HTMLHelper

class ExamDataViewActivity : AppCompatActivity() {

    private var _binding: ActivityExamDataViewBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityExamDataViewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.builtInZoomControls = true
        binding.webView.settings.displayZoomControls = true
        binding.webView.settings.userAgentString =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36"
        binding.webView.settings.useWideViewPort = true
        binding.webView.settings.loadWithOverviewMode = true

        try {
            val htmlHelper = HTMLHelper()
            val item = intent.getIntExtra("item", 0)
            val s = intent.getSerializableExtra("data") as List<*>
            var html = ""
            when (item) {
                1 -> {
                    //time table
                    val data = s as MutableList<TimeTable>
                    html = htmlHelper.getTimeTable(data)
                }
                2 -> {
                    //hall tiket
                }
                3 -> {
                    //result
                    val data = s as MutableList<Result>
                    html = htmlHelper.getResult(data)
                }
                else ->{
                    //something error
                }
            }

            binding.webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)

        } catch (e: Exception) {
            runOnUiThread {
                val er = "plz contact admin for this Error !! <br> $e"
                binding.webView.loadDataWithBaseURL(null, er, "text/html", "UTF-8", null)
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}