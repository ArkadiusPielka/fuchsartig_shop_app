package com.example.fuchsartig.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.fuchsartig.R
import com.example.fuchsartig.data.model.Product
import com.example.fuchsartig.databinding.ListItemLinearBinding
import com.example.fuchsartig.ui.ViewModels.MainViewModel

class LinearAdapter(
    private val dataSet: List<Product>,
    private val sharedViewModel: MainViewModel
) : RecyclerView.Adapter<LinearAdapter.LinearViewHolder>() {


    class LinearViewHolder(val binding: ListItemLinearBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinearViewHolder {
        val binding =
            ListItemLinearBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LinearViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: LinearViewHolder, position: Int) {
        val product = dataSet[position]
        val binding = holder.binding
        val imgUri = product.img1.toUri().buildUpon().scheme("https").build()


        binding.imgProduct.load(imgUri)
        binding.tvPrice.text = product.price
        binding.tvDescription.text = product.descript
        binding.tvTitle.text = product.title

        binding.cwProducts.setOnClickListener {
            sharedViewModel.currentProduct(product)
            sharedViewModel.currentImages(product)
            val navController = holder.itemView.findNavController()
            navController.navigate(R.id.navigation_detail)

        }

        binding.btnLike.setOnClickListener {
            if (!product.is_liked) {
                product.is_liked = true
                binding.btnLike.setImageResource(R.drawable.ic_heart_full)
            } else {
                product.is_liked = false
                binding.btnLike.setImageResource(R.drawable.ic_heart_border)
            }
        }
    }
}