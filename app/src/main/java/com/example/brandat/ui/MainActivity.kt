package com.example.brandat.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.brandat.R
import com.example.brandat.databinding.ActivityMainBinding
import com.example.brandat.databinding.BadgeTextBinding
import com.example.brandat.ui.fragments.cart.IBadgeCount
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IBadgeCount {

    private lateinit var binding: ActivityMainBinding
//    private lateinit var badgeBinding: BadgeTextBinding

    private lateinit var navController: NavController
    private lateinit var notificationsBadges: View
    private var count: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("azza", "onCreate: ", )
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.navHostFragment)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.productDetailsFragment
            ) {
                binding.bottomNavigationView.visibility = View.GONE

            } else {
                binding.bottomNavigationView.visibility = View.VISIBLE
            }

        }

        binding.bottomNavigationView.setupWithNavController(navController)

        binding.imgProfile.setOnClickListener {
            //
            startActivity(Intent(this, ProfileActivity::class.java))
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun updateBadgeCount(count: Int) {
        Log.e("S", "updateBadgeCount:$count")
//        Toast.makeText(this, "$count", Toast.LENGTH_SHORT).show()
//        val itemView = binding.bottomNavigationView.getChildAt(3) as BottomNavigationView
//        notificationsBadges =
//            LayoutInflater.from(this).inflate(R.layout.badge_text, itemView, true)
//        val cartbinding = BadgeTextBinding.bind(notificationsBadges)
//        cartbinding.notificationBadge.text = count.toString()
//        binding.bottomNavigationView.addView(notificationsBadges)

    }

}