package com.example.brandat.ui.fragments.serach

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.brandat.R
import com.example.brandat.databinding.ActivitySearchBinding
import com.example.brandat.ui.fragments.cart.IBadgeCount
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper


@AndroidEntryPoint
class SearchActivity : AppCompatActivity() , ISnackBar, IBadgeCount {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Paper.init(this)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun showSnack() {
        showMessage()
    }

    private fun showMessage() {
        Snackbar.make(binding.cons, getString(R.string.no_connection), Snackbar.LENGTH_LONG)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).setBackgroundTint(
                resources.getColor(
                    R.color.black2
                )
            )
            .setActionTextColor(resources.getColor(R.color.white)).setAction(getString(R.string.close)) {
            }.show()
    }

    override fun updateBadgeCount(count: Int) {
    }

}