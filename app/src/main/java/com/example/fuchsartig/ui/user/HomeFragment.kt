package com.example.fuchsartig.ui.user

import android.graphics.Typeface
import android.graphics.fonts.Font
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.fuchsartig.R
import com.example.fuchsartig.adapter.ViewPageAdapter
import com.example.fuchsartig.databinding.FragmentHomeBinding
import com.example.fuchsartig.databinding.FragmentProductBinding
import com.example.fuchsartig.ui.ViewModels.MainViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val textView = binding.textHome
        val customFont = ResourcesCompat.getFont(requireContext(), R.font.orator_std)
        textView.typeface = customFont

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addObserver()

    }

    private fun addObserver() {
        sharedViewModel.products.observe(viewLifecycleOwner, Observer {
            binding.viewPager2.adapter =
                sharedViewModel.products.value?.let { ViewPageAdapter(it, sharedViewModel) }

            binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            val indicator = binding.indicator
            indicator.setViewPager(binding.viewPager2)
        })
    }
}