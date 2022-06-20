package com.example.brandat.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.example.brandat.R
import com.example.brandat.databinding.ActivityMainBinding
import com.example.brandat.ui.fragments.cart.IBadgeCount
import com.google.android.material.badge.BadgeDrawable
import com.example.brandat.ui.fragments.serach.SearchActivity
import com.example.brandat.utils.ConnectionUtil
import com.example.brandat.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IBadgeCount {

    private lateinit var binding: ActivityMainBinding
    private lateinit var badgeDrawable: BadgeDrawable
    private lateinit var navController: NavController
    private var mCurrentLocale: Locale? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Paper.init(this)

        initUser()

        badgeDrawable = binding.bottomNavigationView.getOrCreateBadge(R.id.cartFragment);
        badgeDrawable.isVisible = true
        if (Paper.book().read<Int>("countFromCart") != null) {
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
        if (Paper.book().read<String>("id") != null) {
            Constants.user.id = Paper.book().read<Long>("id")?.toLong()!!
            Constants.user.email = Paper.book().read<String>("email").toString()
            Constants.user.firstName = Paper.book().read<String>("name").toString()
        }
    }

    override fun onStart() {
        super.onStart()
        mCurrentLocale = getResources().getConfiguration().locale;
    }

    override fun onRestart() {
        super.onRestart()
        val locale = getLocale(this)

        if (!locale!!.equals(mCurrentLocale)) {
            mCurrentLocale = locale
            recreate()
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

    fun getLocale(context: Context?): Locale? {
        val sharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context!!)
        var lang = sharedPreferences.getString("language", "en")
        when (lang) {
            "English" -> lang = "en"
            "Arabic" -> lang = "ar"
        }
        return Locale(lang)
    }
}