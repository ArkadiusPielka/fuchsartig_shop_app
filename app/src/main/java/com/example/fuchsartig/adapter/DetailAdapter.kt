package com.example.fuchsartig.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.fuchsartig.databinding.ListItemDetailBinding
import com.example.fuchsartig.ui.ViewModels.MainViewModel

class DetailAdapter(val dataSet: List<String>, private val sharedViewModel: MainViewModel) :
    RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {

    class DetailViewHolder(val binding: ListItemDetailBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val binding =
            ListItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val image = dataSet[position]
        val binding = holder.binding
        val imgUri1 = image.toUri().buildUpon().scheme("https").build()

        binding.imgProduct.load(imgUri1) {

            transformations(RoundedCornersTransformation(5f))
        }
    }
}