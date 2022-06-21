package com.example.brandat.ui.fragments.registeration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.brandat.R
import com.example.brandat.ui.MainActivity
import com.example.brandat.ui.fragments.cart.IBadgeCount

class RegistrationActivity : AppCompatActivity(), IBadgeCount {

    private lateinit var bageCountI: IBadgeCount

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        bageCountI = MainActivity()

    }

    override fun updateBadgeCount(count: Int) {
    }
}