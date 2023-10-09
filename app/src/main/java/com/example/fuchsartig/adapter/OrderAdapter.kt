package com.example.fuchsartig.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fuchsartig.data.model.Product
import com.example.fuchsartig.databinding.ListItemOrderBinding
import com.example.fuchsartig.ui.ViewModels.AuthViewModel
import com.example.fuchsartig.ui.ViewModels.MainViewModel

class OrderAdapter(
    val dataSet: List<Product>, private val sharedViewModel: MainViewModel,
    private val authViewModel: AuthViewModel
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(val binding: ListItemOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding =
            ListItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val product = dataSet[position]

        val binding = holder.binding

        val price = product.price.toDouble() * product.selectedNumber

        binding.tvTitle.text = product.title
        binding.tvPrice.text = product.price
        binding.tvAmount.text = product.selectedNumber.toString()
        binding.tvTotalPrice.text = String.format("%.2f".format(price))

    }
}