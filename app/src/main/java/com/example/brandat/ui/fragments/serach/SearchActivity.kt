package com.example.brandat.ui.fragments.serach

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.brandat.R
import com.example.brandat.databinding.ActivitySearchBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchActivity : AppCompatActivity() , ISnackBar{

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }

    override fun showSnack() {
        showMessage()
    }

    private fun showMessage() {
        Snackbar.make(binding.cons, "No Connection", Snackbar.LENGTH_LONG)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).setBackgroundTint(
                resources.getColor(
                    R.color.black2
                )
            )
            .setActionTextColor(resources.getColor(R.color.white)).setAction("Close") {
            }.show()
    }


}