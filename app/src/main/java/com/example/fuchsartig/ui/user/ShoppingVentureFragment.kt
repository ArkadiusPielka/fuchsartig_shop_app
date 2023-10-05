package com.example.fuchsartig.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.fuchsartig.R
import com.example.fuchsartig.adapter.ShopCartAdapter
import com.example.fuchsartig.adapter.ViewPageAdapter
import com.example.fuchsartig.data.model.Product
import com.example.fuchsartig.databinding.FragmentDetailBinding
import com.example.fuchsartig.databinding.FragmentShoppingVentureBinding
import com.example.fuchsartig.ui.ViewModels.AuthViewModel
import com.example.fuchsartig.ui.ViewModels.MainViewModel


class ShoppingVentureFragment : Fragment() {

    private lateinit var binding: FragmentShoppingVentureBinding

    private val sharedViewModel: MainViewModel by activityViewModels()

    private val authViewModel: AuthViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShoppingVentureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addObserver()
    }

    //    TODO logik einbinden
    private fun addObserver() {

        authViewModel.shoppingRef.addSnapshotListener { value, error ->
            if (error == null && value != null) {

                val listProduct = mutableListOf<Product>()
                var totalPrice = 0.0

                for (product in value) {
                    val newProduct = product.toObject(Product::class.java)
                    listProduct.add(newProduct)

                    totalPrice += newProduct.price.toDouble() * newProduct.selectedNumber
                }
                binding.rvShoppingVenture.adapter =
                    ShopCartAdapter(listProduct, sharedViewModel, authViewModel)
                authViewModel.buyingProducts = listProduct
                sharedViewModel.updateLayout()

                binding.tvCurrentPrice.text = String.format("%.2f".format(totalPrice))
            }
        }


        binding.tvGoShopping.setOnClickListener {
//            findNavController().navigate(R.id.navigation_product)
        }

//        if (authViewModel.currentPrice.value.isNullOrEmpty()){
//            binding.tvCurrentPrice.text = ""
//            binding.imgEuro.visibility = View.INVISIBLE
//        } else {
//        binding.tvCurrentPrice.text = authViewModel.currentPrice.value.toString()
//        binding.imgEuro.visibility = View.VISIBLE
//        }
    }
}