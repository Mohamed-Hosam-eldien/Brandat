package com.example.brandat.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.brandat.R
import com.example.brandat.databinding.ActivityProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper


@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Paper.init(this)

        initNavWithToolbar()
    }

    private fun initNavWithToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.profile)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navProfileHostFragment) as NavHostFragment

        val navController = navHostFragment.navController

        val navGraph = navController.navInflater.inflate(R.navigation.profile_navigation)

        if (Paper.book().read<String>("email") == null) {
            navGraph.setStartDestination(R.id.loginFragment)
        } else {
            navGraph.setStartDestination(R.id.profileDataFragment)
        }

        navController.graph = navGraph

//        val navController = findNavController(R.id.navProfileHostFragment)
//        navController.graph.setStartDestination(R.id.profileDataFragment)
        val appConfig = AppBarConfiguration(navController.graph)
        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(navController, appConfig)

    }

}