package com.sohezsoft.student.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.sohezsoft.student.R
import com.sohezsoft.student.data.repository.structure.personal.Slider
import com.sohezsoft.student.databinding.ItemImageSliderBinding

class ImageSliderAdapter(private val images: List<Slider>, private val glide: RequestManager) : RecyclerView.Adapter<ImageSliderAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemImageSliderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun loadImage(url : String){
            glide.load(url)
                .placeholder(R.drawable.img_loading_placeholder)
                .into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemImageSliderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.loadImage(images[position].img_url)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}
