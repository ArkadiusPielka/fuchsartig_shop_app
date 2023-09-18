package com.example.fuchsartig.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.fuchsartig.adapter.DetailAdapter
import com.example.fuchsartig.databinding.FragmentDetailBinding
import com.example.fuchsartig.ui.ViewModels.MainViewModel
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.CornerSize
import com.google.android.material.shape.ShapeAppearanceModel


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private val sharedViewModel: MainViewModel by activityViewModels()


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

    fun addObserver() {
        sharedViewModel.currentProduct.observe(viewLifecycleOwner, Observer {product ->

            binding.tvTitle.text = product.title
            binding.tvDescription.text = product.descript


        })
        sharedViewModel.currentImages.observe(viewLifecycleOwner, Observer {
            val viewPager2 = binding.viewPager2
            viewPager2.adapter = DetailAdapter(it,sharedViewModel)

            val indicator = binding.indicator
            indicator.setViewPager(binding.viewPager2)

        })
    }


    fun setCornerRadius() {
        val leftShapePathModel = ShapeAppearanceModel().toBuilder()

        leftShapePathModel.setTopLeftCorner(
            CornerFamily.ROUNDED,
            CornerSize { return@CornerSize 180F })

        leftShapePathModel.setTopRightCorner(
            CornerFamily.ROUNDED,
            CornerSize { return@CornerSize 180F })

    }
}