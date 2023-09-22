package com.example.fuchsartig.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.fuchsartig.data.model.Product
import com.example.fuchsartig.databinding.ListItemShoppingVentureBinding
import com.example.fuchsartig.ui.ViewModels.MainViewModel

class ShopCartAdapter(private val dataSet: List<Product>, private val sharedViewModel: MainViewModel) : RecyclerView.Adapter<ShopCartAdapter.ShopViewHolder>(){

    class ShopViewHolder(val binding: ListItemShoppingVentureBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val binding = ListItemShoppingVentureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShopViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val product = dataSet[position]
        val binding = holder.binding
        val imgUri = product.img1.toUri().buildUpon().scheme("https").build()


        binding.imgProduct.load(imgUri){

            transformations(RoundedCornersTransformation(10f))
        }
        binding.tvPrice.text = product.price
        binding.tvTitle.text = product.title


    }
}