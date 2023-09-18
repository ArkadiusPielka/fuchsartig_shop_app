package com.example.fuchsartig

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fuchsartig.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.background = null

        val config = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.navigation_home,
                R.id.navigation_product,
                R.id.navigation_favorite,
                R.id.navigation_shopping
            )
        )

        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController
        binding.bottomNav.setupWithNavController(navController)

        setupActionBarWithNavController(navController, config)
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
                R.id.navigation_home -> {
                    binding.bottomNav.visibility = View.VISIBLE
                    supportActionBar?.hide()
                }
                else -> {
                    binding.bottomNav.visibility = View.VISIBLE
                    supportActionBar?.hide()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}