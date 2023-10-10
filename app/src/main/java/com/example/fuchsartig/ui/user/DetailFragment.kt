package com.example.fuchsartig.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.fuchsartig.adapter.DetailAdapter
import com.example.fuchsartig.databinding.FragmentDetailBinding
import com.example.fuchsartig.ui.ViewModels.MainViewModel
import android.widget.AdapterView
import androidx.navigation.fragment.findNavController
import com.example.fuchsartig.R
import com.example.fuchsartig.data.model.Product
import com.example.fuchsartig.ui.ViewModels.AuthViewModel


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private val sharedViewModel: MainViewModel by activityViewModels()

    private val authViewModel: AuthViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addObserver()

    }

    private fun addObserver() {
        sharedViewModel.currentProduct.observe(viewLifecycleOwner, Observer { product ->

            binding.tvTitle.text = product.title
            binding.tvDescription.text = product.descript
            binding.tvPrice.text = product.price

            if (authViewModel.currentUser.value?.uid == null) {
                binding.btnLike.visibility = View.INVISIBLE
                binding.btnRegister.visibility = View.VISIBLE
                binding.btnInCart.visibility = View.GONE
                binding.btnBuyNow.visibility = View.GONE
                binding.spinner.visibility = View.GONE
                binding.amountDetail.visibility = View.GONE
            } else {
                binding.btnLike.visibility = View.VISIBLE
                binding.btnRegister.visibility = View.GONE
                binding.btnInCart.visibility = View.VISIBLE
                binding.btnBuyNow.visibility = View.VISIBLE
                binding.spinner.visibility = View.VISIBLE
                binding.amountDetail.visibility = View.VISIBLE
            }

            val liked = authViewModel.favoriteProducts.any { it.apiId == product.apiId }
            if (liked) {
                binding.btnLike.setImageResource(R.drawable.ic_heart_full)
            } else {
                binding.btnLike.setImageResource(R.drawable.ic_heart_border)
            }

            binding.btnInCart.setOnClickListener {
                authViewModel.addToCart(product)
//                findNavController().navigate(R.id.navigation_product)
                //TODO nachricht hinzufügen
            }
            isLiked(product)
            fillSpinner(product)

            binding.btnRegister.setOnClickListener {
                findNavController().navigate(R.id.navigation_login)
            }
        })
        sharedViewModel.currentImages.observe(viewLifecycleOwner, Observer {
            val viewPager2 = binding.viewPager2
            viewPager2.adapter = DetailAdapter(it, sharedViewModel)

            val indicator = binding.indicator
            indicator.setViewPager(binding.viewPager2)

        })
    }

    private fun fillSpinner(product: Product) {

        val data = product.number.toInt()
        val numberList = 1..data
        val spinnerList = mutableListOf<Int>()

        for (i in numberList) {
            spinnerList.add(i)
        }

        val adapter: ArrayAdapter<Int> =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                spinnerList
            )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedAmount = spinnerList[position]
                product.selectedNumber = selectedAmount

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

    }

    private fun isLiked(product: Product) {

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
