package com.sohezsoft.student.screen.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.sohezsoft.student.R
import com.sohezsoft.student.adapter.CardsAdapter
import com.sohezsoft.student.adapter.ImageSliderAdapter
import com.sohezsoft.student.adapter.SearchAdapter
import com.sohezsoft.student.adapter.callback.CardItemClickListener
import com.sohezsoft.student.data.repository.api.NetworkResponse
import com.sohezsoft.student.data.repository.structure.card.Cards
import com.sohezsoft.student.data.repository.structure.personal.Personal
import com.sohezsoft.student.data.repository.structure.personal.Slider
import com.sohezsoft.student.databinding.FragmentHomeBinding
import com.sohezsoft.student.screen.AcademicCalanderActivity
import com.sohezsoft.student.screen.ComplaintActivity
import com.sohezsoft.student.screen.ExamActivity
import com.sohezsoft.student.screen.MainActivity
import com.sohezsoft.student.screen.WebViewActivity
import com.sohezsoft.student.screen.fees.FeesActivity
import com.sohezsoft.student.screen.lecture.LectureActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs

class HomeFragment : Fragment(), CardItemClickListener {

    //create a binding var
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var glide: RequestManager

    //creating the mainActivity variable for access the Activity methods
    //like - viewModel, DialogBox method
    //creating the nullable, becoz onDestory assign the null
    private var mainActivity: MainActivity? = null

    //for Image Slider
    private lateinit var pager: ViewPager2

    //asynchronous and non-blocking exchange of data between different parts of a program.
    //Channels are often used in concurrent or parallel programming to facilitate communication between different threads or coroutines.
    private val searchQueryChannel = Channel<CharSequence>()

    private lateinit var adapter: CardsAdapter
    private lateinit var searchAdapter: SearchAdapter

    private lateinit var cards : List<Cards>

    //this is First Lifecycle Method,
    //in this method we can get the instance of MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Ensure that the hosting context is an instance of MainActivity
        if (context is MainActivity) {
            mainActivity = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        Cards(3, R.drawable.ic_presentee, "Presentee")
//        Cards(5, R.drawable.ic_libray, "Library"),
        cards = listOf(
            Cards(1, R.drawable.ic_fees, "Fees"),
            Cards(2, R.drawable.ic_exam, "Exam"),
            Cards(4, R.drawable.ic_course_main_color, "Lectures"),
            Cards(6, R.drawable.ic_complain, "Complaint"),
            Cards(7, R.drawable.ic_collage, "College Tour"),
            Cards(8, R.drawable.ic_acadmic, "Academic Calender"),
        )

        adapter = CardsAdapter(this)
        adapter.submitList(cards)

        //set the search adapter
        //submit list in editext change
        searchAdapter = SearchAdapter(this)

        //init the glide
        glide = Glide.with(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //init the binding variable
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        //return the View
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //define the ViewPager2
        pager = binding.viewPager

        //On swipe And refresh then call the refreshData() method and refresh the data.
        binding.swipeRefresh.setOnRefreshListener {
            mainActivity?.refreshData()
            //after refreshing hide the loading icon
            binding.swipeRefresh.isRefreshing = false
        }

        //show the loading Dialog Box
        mainActivity?.isLoading(true)

        //setThe Search Edittext
        binding.recycleviewResult.layoutManager = LinearLayoutManager(requireActivity())
        binding.recycleviewResult.adapter = searchAdapter

        //set the Card RecycleView Layout
        //adapter is define in onCreate Method
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerView.hasFixedSize() // use when if you know data is fix size..
        binding.recyclerView.adapter = adapter

        //get the View Model instance from Main Activity
        //do mot init the viewModel in Fragment onCreate,
        //becoz we access the main activity viewmodel that is Not initialize
        //set The Observer for LiveData
        mainActivity?.viewModel?.data?.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResponse.Loading -> {
                    //show the loading Dialog Box
                    mainActivity?.isLoading(true)
                }

                is NetworkResponse.Success -> {
                    //if response is success then, hide the loading Dialog Box
                    mainActivity?.isLoading(false)
                    //Display the Data
                    setupUI(it.data)
                }

                is NetworkResponse.Error -> {
                    //occur the error then hide loading box, and display the error Box
                    mainActivity?.isLoading(false)
                    mainActivity?.showErrorDialog(it.exception.toString())
                }
            }
        }

        //search input Handler
        binding.includedSearch.EditText.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!s.isNullOrEmpty()) {
                    lifecycleScope.launch {
                        searchQueryChannel.send(s)
                    }
                }else{
                    val empty = mutableListOf<Cards>()
                    searchAdapter.submitList(empty)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        //lifecycleScope is valid only Fragment or activity distroy
        lifecycleScope.launch {
            for (searchQuery in searchQueryChannel) {
                val tempList: List<Cards> = cards.filter { it.title.contains(searchQuery, ignoreCase = true) }
                // Update the RecyclerView on the main thread
                withContext(Dispatchers.Main) {
                    if(tempList.isNotEmpty()){
                        searchAdapter.submitList(tempList)
                    }else{
                        val empty = mutableListOf<Cards>()
                        searchAdapter.submitList(empty)
                     //   Toast.makeText(requireContext(),"nodata",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    //setUp the data, loading image, set name etc..
    private fun setupUI(it: Personal) {
        binding.textStudentName.text = it.name
        setupSlider(it.slider)

        glide
            .load(it.photo_url)
            .placeholder(R.drawable.img_user_placeholder)
            .into(binding.circularRevealCardView)
    }

    //this method setup the slider, creating the imageSlider Adapter,
    //set the Adapter to ViewPager2 showing the images
    private fun setupSlider(slider: List<Slider>) {

        val adapter = ImageSliderAdapter(slider, glide)
        pager.adapter = adapter

        pager.offscreenPageLimit = 3
        pager.clipToPadding = false
        pager.clipChildren = false
        pager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        pager.setPageTransformer(transformer)
    }

    //assign the null vale for save the memory leaks
    override fun onDestroyView() {
        _binding = null
        mainActivity?.isLoading(false)
        mainActivity = null
        super.onDestroyView()
    }

    override fun cardItemClicked(id: Int) {
        //go to next acivity
        val i = Intent()
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        when(id){
            1 ->{
                i.setClass(requireContext(), FeesActivity::class.java)
                requireActivity().startActivity(i)
            }
            2 ->{
                i.setClass(requireContext(), ExamActivity::class.java)
                requireActivity().startActivity(i)
            }
            3 ->{

            }
            4 ->{
                i.setClass(requireContext(), LectureActivity::class.java)
                requireActivity().startActivity(i)
            }
            5 ->{

            }
            6 ->{
                i.setClass(requireContext(), ComplaintActivity::class.java)
                requireActivity().startActivity(i)
            }
            7 ->{
                i.setClass(requireContext(), WebViewActivity::class.java)
                requireActivity().startActivity(i)
            }
            else ->{
                i.setClass(requireContext(), AcademicCalanderActivity::class.java)
                requireActivity().startActivity(i)
            }
        }
    }
}