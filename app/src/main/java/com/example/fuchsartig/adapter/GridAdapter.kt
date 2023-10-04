package com.example.fuchsartig.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.fuchsartig.R
import com.example.fuchsartig.data.model.Product
import com.example.fuchsartig.databinding.ListItemGridBinding
import com.example.fuchsartig.ui.ViewModels.AuthViewModel
import com.example.fuchsartig.ui.ViewModels.MainViewModel

class GridAdapter(private val dataSet: List<Product>, private val sharedViewModel: MainViewModel, private val authViewModel: AuthViewModel) :
    RecyclerView.Adapter<GridAdapter.GridViewHolder>() {


    class GridViewHolder(val binding: ListItemGridBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val binding =
            ListItemGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GridViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val product = dataSet[position]
        val binding = holder.binding
        val imgUri = product.img1.toUri().buildUpon().scheme("https").build()

        binding.imgProduct.load(imgUri)
        binding.tvTitle.text = product.title

        binding.cwProducts.setOnClickListener {
            sharedViewModel.currentProduct(product)
            sharedViewModel.currentImages(product)
            val navController = holder.itemView.findNavController()
            navController.navigate(R.id.navigation_detail)

        }

        for (product in dataSet) {
            product.liked = authViewModel.favoriteProducts.any { it.apiId == product.apiId }
        }

        if (product.liked){
            binding.btnLike.setImageResource(R.drawable.ic_heart_full)
        } else {
            binding.btnLike.setImageResource(R.drawable.ic_heart_border)
        }

        binding.btnLike.setOnClickListener {
            if (!product.liked) {
                product.liked = true
                authViewModel.addFavorites(product)
                binding.btnLike.setImageResource(R.drawable.ic_heart_full)
            } else {
                product.liked = false
                authViewModel.removeFavorites(product)
                binding.btnLike.setImageResource(R.drawable.ic_heart_border)
            }
        }
    }
}