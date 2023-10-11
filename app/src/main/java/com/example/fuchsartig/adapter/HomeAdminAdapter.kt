package com.example.fuchsartig.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.fuchsartig.data.model.Product
import com.example.fuchsartig.databinding.ListItemHomeAdminBinding
import com.example.fuchsartig.ui.ViewModels.AuthViewModel
import com.example.fuchsartig.ui.ViewModels.MainViewModel

class HomeAdminAdapter(private val dataSet: List<Product>, private val sharedViewModel: MainViewModel, private val authViewModel: AuthViewModel) :
    RecyclerView.Adapter<HomeAdminAdapter.HomeAdminViewHolder>() {

        class HomeAdminViewHolder(val binding: ListItemHomeAdminBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdminViewHolder {
        val binding = ListItemHomeAdminBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return HomeAdminViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: HomeAdminViewHolder, position: Int) {
        val product = dataSet[position]
        val binding = holder.binding
        val imgUri = product.img1.toUri().buildUpon().scheme("https").build()

        binding.imgProduct.load(imgUri)
        binding.tvTitle.text = product.title
        binding
    }

}