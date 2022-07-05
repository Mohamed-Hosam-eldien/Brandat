package com.example.brandat.welcomescreen

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.brandat.R
import com.example.brandat.ui.MainActivity


class SplashyFragment : Fragment() {

    lateinit var btn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.myLooper()!!).postDelayed({
            if (isOpenAlread() == false) {
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } else{
                val editor = context?.getSharedPreferences("slide", AppCompatActivity.MODE_PRIVATE)?.edit()
                editor?.putBoolean("slide", true)
                editor?.commit()
                findNavController().navigate(R.id.action_splashyFragment_to_sliderFragment)
            }


        }, 3000)

    }

    private fun isOpenAlread(): Boolean? {
        val sharedPreferences: SharedPreferences? =
            context?.getSharedPreferences("slide", Context.MODE_PRIVATE)
        return sharedPreferences?.getBoolean("slide", false)

    }
}