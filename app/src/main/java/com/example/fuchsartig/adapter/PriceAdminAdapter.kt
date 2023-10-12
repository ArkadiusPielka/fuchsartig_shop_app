package com.example.fuchsartig.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.fuchsartig.data.model.Product
import com.example.fuchsartig.databinding.ListItemHomeAdminBinding
import com.example.fuchsartig.databinding.ListItemPriceAdminBinding
import com.example.fuchsartig.ui.ViewModels.MainViewModel

class PriceAdminAdapter(
    private val dataSet: List<Product>,
    private val sharedViewModel: MainViewModel
) :
    RecyclerView.Adapter<PriceAdminAdapter.PriceAdminViewHolder>() {

        class PriceAdminViewHolder(val binding: ListItemPriceAdminBinding):
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceAdminViewHolder {
        val binding =
            ListItemPriceAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PriceAdminViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: PriceAdminViewHolder, position: Int) {
        val product = dataSet[position]
        val binding = holder.binding
        val imgUri = product.img1.toUri().buildUpon().scheme("https").build()

        binding.imgProduct.load(imgUri)
        binding.tvTitle.text = product.title
        binding.tvPrice.text = product.price

        binding.btnSave.setOnClickListener {
            binding.inputPrice
        }
    }
}