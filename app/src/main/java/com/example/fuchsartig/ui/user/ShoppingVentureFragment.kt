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
                var productAmount = 0

                for (product in value) {
                    val newProduct = product.toObject(Product::class.java)
                    listProduct.add(newProduct)

                    totalPrice += newProduct.price.toDouble() * newProduct.selectedNumber
                    productAmount += newProduct.selectedNumber

                }
                binding.rvShoppingVenture.adapter =
                    ShopCartAdapter(listProduct, sharedViewModel, authViewModel)
                authViewModel.buyingProducts = listProduct
                sharedViewModel.updateLayout()

                if (listProduct.isEmpty()) {
                    binding.tvCurrentPrice.text = ""
                    binding.imgEuro.visibility = View.INVISIBLE
                    binding.favoriteFiller.visibility = View.VISIBLE
                    binding.cvShoppingCart.visibility = View.GONE
//                    binding.btnBuy.text = getString(R.string.buy_artikel, ammount.toString())
                } else {
                    binding.favoriteFiller.visibility = View.GONE
                    binding.cvShoppingCart.visibility = View.VISIBLE
                    binding.tvCurrentPrice.text = String.format("%.2f".format(totalPrice))
//                    binding.btnBuy.text = getString(R.string.buy_artikel, productAmount.toString())

                }
                if (isAdded) {
                    if (binding != null && binding.btnBuy != null) {
                        if (value.isEmpty) {
                            binding.btnBuy.text = getString(R.string.buy_artikel_0)
                        } else {
                            binding.btnBuy.text = getString(R.string.buy_artikel, productAmount.toString())
                        }
                    }
                }
            }
        }

        binding.tvGoShopping.setOnClickListener {
//            findNavController().navigate(R.id.navigation_product)
        }

        binding.btnBuy.setOnClickListener {
            findNavController().navigate(R.id.orderFragment)
        }
    }
}