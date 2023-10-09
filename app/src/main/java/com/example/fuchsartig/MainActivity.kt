package com.example.fuchsartig

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fuchsartig.databinding.ActivityMainBinding
import com.example.fuchsartig.ui.ViewModels.AuthViewModel
import com.example.fuchsartig.ui.ViewModels.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    private lateinit var sharedViewModel: MainViewModel

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.background = null



        bottemNavUser()


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun bottemNavUser(){
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController
        binding.bottomNav.setupWithNavController(navController)

        setupActionBarWithNavController(navController)
        binding.bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_detail -> {
                    binding.bottomNav.visibility = View.GONE
                    supportActionBar?.show()
                }
                R.id.navigation_login -> {
                    binding.bottomNav.visibility = View.GONE
                    supportActionBar?.hide()
                }
                R.id.navigation_register -> {
                    binding.bottomNav.visibility = View.GONE
                    supportActionBar?.show()
                }
                R.id.navigation_profil -> {
                    binding.bottomNav.visibility = View.GONE
                    supportActionBar?.show()
                }
                R.id.orderFragment ->{
                    binding.bottomNav.visibility = View.GONE
                    supportActionBar?.show()
                }
                else -> {
                    binding.bottomNav.visibility = View.VISIBLE
                    supportActionBar?.hide()
                }
            }
        }
    }


}