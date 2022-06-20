package com.example.brandat.ui

import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.brandat.R
import com.example.brandat.databinding.ActivityMainBinding
import com.example.brandat.ui.fragments.cart.IBadgeCount
import com.google.android.material.badge.BadgeDrawable
import com.example.brandat.ui.fragments.serach.SearchActivity
import com.example.brandat.utils.Constants
import com.example.brandat.ui.fragments.orderStatus.checkOutOrder.OnOkClickListener
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IBadgeCount {

    private lateinit var binding: ActivityMainBinding
    private lateinit var badgeDrawable: BadgeDrawable
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Paper.init(this)

        initUser()

        badgeDrawable = binding.bottomNavigationView.getOrCreateBadge(R.id.cartFragment);
        badgeDrawable.isVisible = true
        if(Paper.book().read<Int>("countFromCart")!=null) {
            badgeDrawable.number = Paper.book().read<Int>("countFromCart")!!
        }
        if (badgeDrawable.number == 0) {
            badgeDrawable.isVisible = false
        }

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

        binding.search.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

    }

    private fun initUser() {
        if(Paper.book().read<String>("id") != null ) {
            Constants.user.id = Paper.book().read<Long>("id")?.toLong()!!
            Constants.user.email = Paper.book().read<String>("email").toString()
            Constants.user.firstName = Paper.book().read<String>("name").toString()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNavigationView.visibility = View.VISIBLE
        binding.toolbar.visibility = View.VISIBLE
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun updateBadgeCount(count: Int) {
        Toast.makeText(this, "$count", Toast.LENGTH_SHORT).show()
        badgeDrawable = binding.bottomNavigationView.getOrCreateBadge(R.id.cartFragment);
        badgeDrawable.isVisible = true
        badgeDrawable.number = count
        if (badgeDrawable.number == 0) {
            badgeDrawable.isVisible = false
        }
    }

}