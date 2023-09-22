package com.example.fuchsartig.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.example.fuchsartig.R
import com.example.fuchsartig.adapter.ShopCartAdapter
import com.example.fuchsartig.adapter.ViewPageAdapter
import com.example.fuchsartig.databinding.FragmentDetailBinding
import com.example.fuchsartig.databinding.FragmentShoppingVentureBinding
import com.example.fuchsartig.ui.ViewModels.MainViewModel


class ShoppingVentureFragment : Fragment() {

    private lateinit var binding: FragmentShoppingVentureBinding

    private val sharedViewModel: MainViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShoppingVentureBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addObserver()
    }

//    TODO logik einbinden
    private fun addObserver() {
        sharedViewModel.products.observe(viewLifecycleOwner, Observer {
            binding.rvShoppingVenture.adapter = ShopCartAdapter(it, sharedViewModel)


        })
    }
}