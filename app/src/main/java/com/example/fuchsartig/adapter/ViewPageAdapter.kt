package com.example.fuchsartig.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.fuchsartig.R
import com.example.fuchsartig.data.model.Product
import com.example.fuchsartig.databinding.ListItemHomeBinding
import com.example.fuchsartig.ui.ViewModels.MainViewModel

class ViewPageAdapter(val dataSet: List<Product>,private val sharedViewModel: MainViewModel): RecyclerView.Adapter<ViewPageAdapter.Pager2ViewHolder>() {

    inner class Pager2ViewHolder(val binding: ListItemHomeBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pager2ViewHolder {
        val binding = ListItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Pager2ViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return dataSet.size
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        val product = dataSet[position]
        val binding = holder.binding
        val imgUri = product.img1.toUri().buildUpon().scheme("https").build()

        binding.imgProduct.load(imgUri) {

            transformations(RoundedCornersTransformation(10f))
        }

        binding.imgProduct.setOnClickListener {
            sharedViewModel.currentProduct(product)
            sharedViewModel.currentImages(product)
            val navController = holder.itemView.findNavController()
            navController.navigate(R.id.navigation_detail)

        }
    }


}