package com.example.brandat.ui.fragments.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.brandat.R
import com.example.brandat.databinding.FragmentProfileDataBinding
import com.example.brandat.ui.OrderStatus
import com.example.brandat.ui.Profile

class ProfileDataFragment : Fragment() {

    private lateinit var binding:FragmentProfileDataBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_profile_data, container, false)

        binding = FragmentProfileDataBinding.bind(view)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.orderLayout.setOnClickListener{
            // navigate to orders fragment
        }
        binding.languageLayout.setOnClickListener{
            // navigate to language fragment
        }
        binding.addressLayout.setOnClickListener{
            // navigate to addresses fragment
            it.findNavController().navigate(R.id.action_profileDataFragment_to_addressFragment)
        }
        binding.currencyLayout.setOnClickListener{
            // navigate to currency fragment
            it.findNavController().navigate(R.id.action_profileDataFragment_to_currencyFragment)
        }

    }



}