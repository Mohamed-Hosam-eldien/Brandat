package com.example.brandat.welcomescreen

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.brandat.R
import com.example.brandat.ui.MainActivity
import io.paperdb.Paper
import org.objectweb.asm.Handle


class SplashyFragment : Fragment() {

    lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_splashy, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.myLooper()!!).postDelayed({
            if (isOpenAlread() == true) {
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } else{
                val editor = context?.getSharedPreferences("slide", AppCompatActivity.MODE_PRIVATE)?.edit()
                editor?.putBoolean("slide", true)
                editor?.commit()
                findNavController().navigate(R.id.action_splashyFragment_to_sliderFragment)
            }


        }, 5000)

    }

    private fun isOpenAlread(): Boolean? {
        val sharedPreferences: SharedPreferences? =
            context?.getSharedPreferences("slide", Context.MODE_PRIVATE)
        return sharedPreferences?.getBoolean("slide", false)

    }
}