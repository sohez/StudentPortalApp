package com.sohezsoft.student.screen.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sohezsoft.student.R
import com.sohezsoft.student.adapter.SettingAdapter
import com.sohezsoft.student.data.repository.db.preference.TokenPreference
import com.sohezsoft.student.data.repository.structure.setting.SettingItemData
import com.sohezsoft.student.databinding.FragmentSettingBinding
import com.sohezsoft.student.screen.RegisterActivity

class SettingFragment : Fragment(), SettingAdapter.SettingItemClickEvent{

    private var _binding : FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private lateinit var settingItemData : List<SettingItemData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingItemData = listOf(
            SettingItemData(
                1,
                "Log Out",
                R.drawable.ic_lock
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.includedLayout.textViewTitle.text = "Setting"
        binding.includedLayout.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.includedLayout.recyclerView.hasFixedSize()
        val adapter = SettingAdapter(this)
        binding.includedLayout.recyclerView.adapter = adapter
        adapter.submitList(settingItemData)
    }

    private fun logOut(){
        TokenPreference(requireActivity()).removeToken()
        requireActivity().startActivity(Intent(requireActivity(),RegisterActivity::class.java))
        requireActivity().finish()
    }

    override fun onDestroy() {
        _binding=null
        super.onDestroy()
    }

    override fun settingItemClicked(itemId: Int) {
        //itemId is not position it is id of ListSettingItem
        when(itemId){
            1 ->{
                logOut()
            }
        }
    }

}