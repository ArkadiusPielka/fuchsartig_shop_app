package com.example.fuchsartig.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fuchsartig.R
import com.example.fuchsartig.adapter.GridAdapter
import com.example.fuchsartig.adapter.LinearAdapter
import com.example.fuchsartig.data.model.MasterCard
import com.example.fuchsartig.data.model.Product
import com.example.fuchsartig.databinding.FragmentDetailBinding
import com.example.fuchsartig.databinding.FragmentFavoriteBinding
import com.example.fuchsartig.ui.ViewModels.ApiLayoutStatus
import com.example.fuchsartig.ui.ViewModels.AuthViewModel
import com.example.fuchsartig.ui.ViewModels.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding

    private val sharedViewModel: MainViewModel by activityViewModels()

    private val authViewModel: AuthViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        addObserver()

        authViewModel.favoritesRef.addSnapshotListener { value, error ->
            if (error == null && value != null) {
                val listProduct = mutableListOf<Product>()
                for (product in value) {
                    val newProduct = product.toObject(Product::class.java)
                    listProduct.add(newProduct)
                }
                authViewModel.favoriteProducts = listProduct
                sharedViewModel.updateLayout()
            }
        }
    }

    private fun addObserver() {

//        if (authViewModel.favoritesRef.document()) {
//        binding.favoriteFiller.visibility = View.VISIBLE
//        binding.rvShoppingVenture.visibility = View.GONE
//        } else {
            binding.favoriteFiller.visibility = View.GONE
            binding.rvShoppingVenture.visibility = View.VISIBLE
//        }



        sharedViewModel.layout.observe(viewLifecycleOwner, Observer { status ->

            when (status) {

                ApiLayoutStatus.LINEAR -> {

                    binding.rvShoppingVenture.layoutManager = LinearLayoutManager(requireContext())
                    binding.rvShoppingVenture.adapter = LinearAdapter(
                        authViewModel.favoriteProducts,
                        sharedViewModel,
                        authViewModel
                    )

                    binding.cvSortVertical.setCardBackgroundColor(
                        ContextCompat.getColor(requireContext(), R.color.primary_color)
                    )
                    binding.cvSortHorizontal.setCardBackgroundColor(
                        ContextCompat.getColor(requireContext(), R.color.white)
                    )
                }

                ApiLayoutStatus.GRID -> {

                    binding.rvShoppingVenture.layoutManager = GridLayoutManager(requireContext(), 2)
                    binding.rvShoppingVenture.adapter =
                        GridAdapter(authViewModel.favoriteProducts, sharedViewModel, authViewModel)

                    binding.cvSortVertical.setCardBackgroundColor(
                        ContextCompat.getColor(requireContext(), R.color.white)
                    )
                    binding.cvSortHorizontal.setCardBackgroundColor(
                        ContextCompat.getColor(requireContext(), R.color.primary_color)
                    )
                }
            }
        })

    }

    private fun setupButtons() {

        val slideRight = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_right)

        binding.cvSortVertical.setOnClickListener {

            sharedViewModel.setApiLayoutStatus(ApiLayoutStatus.LINEAR)
            binding.rvShoppingVenture.startAnimation(slideRight)

        }

        binding.cvSortHorizontal.setOnClickListener {

            sharedViewModel.setApiLayoutStatus(ApiLayoutStatus.GRID)
            binding.rvShoppingVenture.startAnimation(slideRight)

        }

    }
}