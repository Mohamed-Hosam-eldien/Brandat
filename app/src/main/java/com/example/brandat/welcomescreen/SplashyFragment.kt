package com.example.brandat.welcomescreen

import android.content.Context.MODE_PRIVATE
import android.content.Intent
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_splash, container, false)
        Handler(Looper.myLooper()!!).postDelayed({
            findNavController().navigate(R.id.action_splashyFragment_to_sliderFragment)
        }, 5000)
//        btn = view.findViewById(R.id.btn_i)
//
//
//        btn.setOnClickListener {
//            findNavController().navigate(R.id.action_splashyFragment_to_sliderFragment)
//        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//            Timer("SettingUp", false).schedule(3000) {
//                requireActivity().runOnUiThread {
//           findNavController().navigate(R.id.action_splashyFragment_to_sliderFragment)
//
//                }
//            }
//        Paper.init(requireContext())
//        val flag = Paper.book().read<Boolean>("slider")
//

        // Toast.makeText(context, "$flag", Toast.LENGTH_SHORT).show()
        if (isOpenAlread()) {

            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

        } else {

            val editor =
                context?.getSharedPreferences("slide", AppCompatActivity.MODE_PRIVATE)?.edit()
            editor?.putBoolean("slide", true)
            editor?.commit()
        }
    }

    private fun isOpenAlread(): Boolean {

        val sheardPreferec = context?.getSharedPreferences("slide", MODE_PRIVATE)
        var result = sheardPreferec?.getBoolean("slide", false)
        return result!!
    }

}